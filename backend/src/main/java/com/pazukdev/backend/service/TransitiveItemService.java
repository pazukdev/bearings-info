package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.TransitiveItemConverter;
import com.pazukdev.backend.dto.item.ItemDescriptionMap;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.dto.item.ItemQuantity;
import com.pazukdev.backend.dto.item.ReplacerData;
import com.pazukdev.backend.dto.table.ItemView;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import com.pazukdev.backend.entity.item.TransitiveItem;
import com.pazukdev.backend.repository.TransitiveItemRepository;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.SpecificStringUtil;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.*;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class TransitiveItemService extends AbstractService<TransitiveItem, ItemDto> {

    @Getter
    private final TransitiveItemRepository transitiveItemRepository;

    public TransitiveItemService(final TransitiveItemRepository transitiveItemRepository,
                                 final TransitiveItemConverter converter) {
        super(transitiveItemRepository, converter);
        this.transitiveItemRepository = transitiveItemRepository;
    }

    @Transactional
    public ItemView getItem(final Long id) throws EntityExistsException {
        return createItemView(getOne(id));
    }

    @Transactional
    public ItemView update(final Long id, final ItemView itemView) {
        final ItemDto oldItem = itemView.getItem();
        oldItem.setId(id);
        final String oldName = oldItem.getName();
        final TableDto header = itemView.getHeader();
        final String[][] headerMatrix = header.getMatrix();
        final Map<String, String> headerMatrixMap = new HashMap<>();
        for (final String[] row : headerMatrix) {
            headerMatrixMap.put(row[0], row[1]);
        }

        String[] newParameter = null;
        final Map<String, String> oldItemDescriptionMap = ItemUtil.toMap(oldItem.getDescription());
        for (final Map.Entry<String, String> entry : headerMatrixMap.entrySet()) {
            final String parameter = entry.getKey();
            final String newValue = entry.getValue();
            final String oldValue = oldItemDescriptionMap.get(parameter);
            if (oldValue == null) {
                newParameter = new String[]{parameter, newValue};
            } else {
                if (!oldValue.equals(newValue)) {
                    oldItemDescriptionMap.replace(parameter, newValue);
                }
            }
            if (newParameter != null) {
                oldItemDescriptionMap.put(newParameter[0], newParameter[1]);
            }

        }
        final String newDescription = ItemUtil.toDescription(oldItemDescriptionMap);
        oldItem.setDescription(newDescription);
        transitiveItemRepository.save(converter.convertToEntity(oldItem));

        final String newName = headerMatrixMap.get("Name");
        if (newName != null && !newName.equals(oldName)) {
            oldItem.setName(newName);
            transitiveItemRepository.save(converter.convertToEntity(oldItem));
            replaceEverywhere(getOne(id), oldName, newName);
        }

        return getItem(id);
    }

    private void replaceEverywhere(final TransitiveItem editingItem,
                                   final String oldValue,
                                   final String newValue) {
        final String editingItemCategory = editingItem.getCategory();
        final List<TransitiveItem> items = findAll();
        final Set<String> categories = ItemUtil.getCategories(items);
        for (final TransitiveItem item : items) {
            final Map<String, String> descriptionMap = ItemUtil.toMap(item.getDescription());
            for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
                final String value = entry.getValue().split(" \\(")[0];
                if (value.equals(oldValue)) {
                    if (categories.contains(entry.getKey())) {
                        if (entry.getKey().equals(editingItemCategory)) {
                            entry.setValue(entry.getValue().replace(oldValue, newValue));
                        }
                    } else {
                        entry.setValue(entry.getValue().replace(oldValue, newValue));
                    }
                }
            }
            final String newDescription = ItemUtil.toDescription(descriptionMap);
            item.setDescription(newDescription);

            final String replacer = item.getReplacer();
            if (item.getCategory().equals(editingItemCategory) && replacer.contains(oldValue)) {
                final String newReplacer = replacer.replace(oldValue, newValue);
                item.setReplacer(newReplacer);
            }
            transitiveItemRepository.save(item);
        }
    }

    public ItemDescriptionMap createDescriptionMap(final TransitiveItem item) {
        return ItemUtil.createDescriptionMap(item, this);
    }

    public ItemView createItemView(final TransitiveItem item) {
        final ItemDescriptionMap descriptionMap = createDescriptionMap(item);
        final ItemView itemView = new ItemView();
        final ItemDto dto = converter.convertToDto(item);
        itemView.setItem(dto);
        itemView.setHeader(createHeader(item, descriptionMap));
        itemView.setSelectableData(createSelectableCharacteristics(descriptionMap));
        itemView.setItems(createTableView(descriptionMap));
        itemView.setReplacers(createReplacers(item));
        return itemView;
    }

    private TableDto createHeader(final TransitiveItem item, final ItemDescriptionMap descriptionMap) {
        final List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Name", item.getName()});
        final String tableName = item.getCategory().replace(" (i)", "") + " " + item.getName();
        return createTable(tableName, descriptionMap.getCharacteristics(), list);
    }

    private TableDto createTable(final String tableName, final Map<String, String> map, final List<String[]> list) {
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            list.add(new String[]{entry.getKey(), entry.getValue()});
        }
        return new TableDto(tableName, listToMatrix(list));
    }

    private TableDto createSelectableCharacteristics(final ItemDescriptionMap descriptionMap) {
        final String noName = "";
        final List<String[]> list = new ArrayList<>();
        for (final Map.Entry<String, String> entry : descriptionMap.getSelectableCharacteristics().entrySet()) {
            final String category = entry.getKey();
            final TransitiveItem selectable = createSelectableItem(entry.getValue(), category);
            list.add(new String[]{
                    category.replace(" (i)", ""),
                    selectable.getName(),
                    selectable.getId() != null ? selectable.getId().toString() : "no id"});
        }
        return new TableDto(noName, listToMatrix(list));
    }

    private TableDto createReplacers(final TransitiveItem item) {
        final List<ReplacerData> replacersData = ItemUtil.parseReplacersSourceString(item.getReplacer());
        final List<String[]> list = getReplacers(item.getCategory(), replacersData);
        final String[][] matrix = listToMatrix(list);
        return new TableDto(matrix.length > 0 ? "Replacers" : null, matrix);
    }

    private List<String[]> getReplacers(final String category,
                                        final List<ReplacerData> replacersData) {
        final Map<TransitiveItem, String> map = new HashMap<>();

        for (final ReplacerData replacerData : replacersData) {
            final TransitiveItem replacer = find(category, replacerData.getName());
            if (replacer != null) {
                map.put(replacer, replacerData.getComment());
            }
        }

        final List<String[]> data = new ArrayList<>();
        for (final Map.Entry<TransitiveItem, String> entry : map.entrySet()) {
            final TransitiveItem replacer = entry.getKey();
            final String comment = entry.getValue();
            data.add(new String[]{
                    comment,
                    createReplacerButtonText(replacer),
                    replacer.getId().toString()});
        }
        return data;
    }

    private String createReplacerButtonText(final TransitiveItem replacer) {
        if (isAddManufacturerName(replacer)) {
            return ItemUtil.getValueFromDescription(replacer, "Manufacturer") + " " + replacer.getName();
        } else {
            return replacer.getName();
        }
    }

    private boolean isAddManufacturerName(final TransitiveItem replacer) {
        final String category = replacer.getCategory();
        return category.equals("Seal") || category.equals("Spark plug");
    }

    public ItemView motorcycleCatalogue() {
        final List<TransitiveItem> motorcycles = find("Motorcycle");

        final String tableName = "Motorcycle catalogue";
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Models", String.valueOf(motorcycles.size())});

        final ItemView itemView = new ItemView();
        itemView.setHeader(new TableDto(tableName, listToMatrix(list)));
        itemView.setSelectableData(stubTable());
        itemView.setItems(new TableViewDto(22, new ArrayList<>(Collections.singletonList(motorcyclesTable(motorcycles)))));
        itemView.setReplacers(stubTable());
        return itemView;
    }

    private TableDto stubTable() {
        return new TableDto("stub", new String[][]{{""}});
    }

    private String[][] listToMatrix(List<String[]> list) {
        int j = 0;
        String[][] matrix = new String[list.size()][];
        for (String[] s : list) {
            matrix[j++] = s;
        }
        return matrix;
    }

    public TableDto motorcyclesTable(final List<TransitiveItem> motorcycles) {
        final String tableName = "Motorcycles";
        final List<String[]> rows = new ArrayList<>();
        for (final TransitiveItem motorcycle : motorcycles) {
            final String[] row = {
                    ItemUtil.getValueFromDescription(motorcycle.getDescription(), "Production"),
                    motorcycle.getName(),
                    ItemUtil.getValueFromDescription(motorcycle.getDescription(), "Manufacturer"),
                    motorcycle.getId().toString()};
            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

    public TableViewDto createTableView(final ItemDescriptionMap descriptionMap) {
        final List<ItemQuantity> itemQuantities = createItemQuantities(descriptionMap);
        return createTableView(descriptionMap.getParent().getName(), itemQuantities);
    }

    private List<ItemQuantity> createItemQuantities(final ItemDescriptionMap descriptionMap) {
        final List<ItemQuantity> itemQuantities = new ArrayList<>();
        for (final Map.Entry entry : descriptionMap.getItems().entrySet()) {
            final String category = entry.getKey().toString();
            if (entry.getValue().toString().contains(";")) {
                final String[] names = entry.getValue().toString().split("; ");
                for (final String name : names) {
                    final ItemQuantity itemQuantity = createItemQuantity(name,category);
                    if (itemQuantity != null) {
                        itemQuantities.add(itemQuantity);
                    }
                }
            } else {
                final String name = entry.getValue().toString();
                final ItemQuantity itemQuantity = createItemQuantity(name, category);
                if (itemQuantity != null) {
                    itemQuantities.add(itemQuantity);
                }
            }
        }

        return itemQuantities;
    }

    private ItemQuantity createItemQuantity(final String value, final String category) {
        String name;
        String location = "";
        String quantity;
        if (SpecificStringUtil.containsParentheses(value)) {
            name = SpecificStringUtil.getStringBeforeParentheses(value);
            String additionalData = SpecificStringUtil.getStringBetweenParentheses(value);
            location = additionalData.contains(" - ") ? additionalData.split(" - ")[0] : "-";
            quantity = additionalData.contains(" - ") ? additionalData.split(" - ")[1] : additionalData;
        } else {
            name = value;
            location = "-";
            quantity = category.equals("Spark plug") ? "2" : "1";
        }
        final TransitiveItem child = category.equals("Seal") ? getUssrSealBySize(name) : find(category, name);
        if (child != null) {
            final ItemQuantity itemQuantity  = new ItemQuantity();
            itemQuantity.setItem(child);
            itemQuantity.setLocation(location);
            itemQuantity.setQuantity(quantity);
            return itemQuantity;
        } else {
            return null;
        }
    }

    private TransitiveItem createSelectableItem(final String value, final String category) {
        final TransitiveItem selectable = find(category + " (i)", value);
        if (selectable != null) {
            return selectable;
        } else {
            final TransitiveItem selectableStub = new TransitiveItem();
            selectableStub.setCategory(category);
            selectableStub.setName(value);
            return selectableStub;
        }
    }

    public TransitiveItem getUssrSealBySize(final String searchingSize) {
        final List<TransitiveItem> ussrSeals = filter(find("Seal"), "Manufacturer", "USSR");
        for (TransitiveItem seal : ussrSeals) {
            final String actualSize = ItemUtil.getValueFromDescription(seal.getDescription(), "Size, mm");
            if (actualSize.equals(searchingSize)) {
                return seal;
            }
        }
        return null;
    }

    private List<TransitiveItem> filter(final List<TransitiveItem> items,
                                        final String parameter,
                                        final String searchingValue) {
        final List<TransitiveItem> filteredItems = new ArrayList<>();
        for (TransitiveItem item : items) {
            final String value = ItemUtil.getValueFromDescription(item.getDescription(), parameter);
            if (value != null && value.equals(searchingValue)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public TableViewDto createTableView(final String parentItemName, final List<ItemQuantity> quantities) {

        final List<TableDto> tables = new ArrayList<>();
        for (final List<ItemQuantity> categorizedItems : ItemUtil.categorizeItemQuantities(quantities)) {
            tables.add(createTable(categorizedItems));
        }
        return new TableViewDto(quantities.size(), tables);
    }

    public TableDto createTable(final List<ItemQuantity> itemQuantities) {
        final String tableName = itemQuantities.get(0).getItem().getCategory();
        final List<String[]> rows = new ArrayList<>();
        for (final ItemQuantity itemQuantity : itemQuantities) {
            final TransitiveItem item = itemQuantity.getItem();
            final String[] row = {
                    itemQuantity.getLocation(),
                    item.getCategory().equals("Seal") ?
                            ItemUtil.getValueFromDescription(item, "Size, mm") : item.getName(),
                    itemQuantity.getQuantity() != null ? itemQuantity.getQuantity() : "0",
                    item.getId().toString()
            };

            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

    @Transactional
    @Override
    public TransitiveItem findByName(final String name) {
        return ((TransitiveItemRepository) transitiveItemRepository).findByName(name);
    }

    @Transactional
    public List<TransitiveItem> find(final String category, final String... names) {
        final List<TransitiveItem> items = new ArrayList<>();
        for (final String name : names) {
            items.add(find(category, name));
        }
        return items;
    }

    @Transactional
    public TransitiveItem find(final String category, final String name) {
        for (final TransitiveItem item : find(category)) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Transactional
    public List<TransitiveItem> find(final String category) {
        final List<TransitiveItem> categorizedItems = new ArrayList<>();
        for (final TransitiveItem item : findAll()) {
            if (item.getCategory().toLowerCase().equals(category.toLowerCase())) {
                categorizedItems.add(item);
            }
        }
        return categorizedItems;
    }

}




















