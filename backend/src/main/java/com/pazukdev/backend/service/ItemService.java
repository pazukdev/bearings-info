package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.dto.item.TransitiveItemDescriptionMap;
import com.pazukdev.backend.dto.item.TransitiveItemDto;
import com.pazukdev.backend.dto.table.ItemView;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import com.pazukdev.backend.entity.item.ChildItem;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.Replacer;
import com.pazukdev.backend.entity.item.TransitiveItem;
import com.pazukdev.backend.repository.ItemRepository;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.SpecificStringUtil;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.*;

import static com.pazukdev.backend.util.ItemUtil.createDescriptionMap;
import static com.pazukdev.backend.util.SpecificStringUtil.replaceBlankWithDash;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ItemService extends AbstractService<Item, TransitiveItemDto> {

    final TransitiveItemService transitiveItemService;
    @Getter
    private final ItemRepository itemRepository;

    public ItemService(final ItemRepository itemRepository,
                       final ItemConverter converter,
                       final TransitiveItemService transitiveItemService) {
        super(itemRepository, converter);
        this.transitiveItemService = transitiveItemService;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Item findByName(String name) {
        return ((ItemRepository) repository).findByName(name);
    }

    @Transactional
    public Item find(final String category, final String name) {
        for (final Item item : find(category)) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Transactional
    public List<Item> find(final String category) {
        final List<Item> categorizedItems = new ArrayList<>();
        for (final Item item : findAll()) {
            if (item.getCategory().toLowerCase().equals(category.toLowerCase())) {
                categorizedItems.add(item);
            }
        }
        return categorizedItems;
    }

    @Transactional
    public Item saveAsItem(final TransitiveItem transitiveItem) {
        final Item item = getOrCreate(transitiveItem);
        item.setName(replaceBlankWithDash(item.getName()));
        return repository.save(item);
    }

    @Transactional
    public ItemView createItemView(final Long id) throws EntityExistsException {
        return createItemView(getOne(id));
    }

    @Transactional
    public ItemView update(final Long id, final ItemView itemView) {
        final Item item = getOne(id);
        final Map<String, String> headerMatrixMap = createHeaderMatrixMap(itemView);
        updateName(item, headerMatrixMap);
        updateDescription(item, headerMatrixMap);
        itemRepository.save(item);
        return createItemView(id);
    }

    private Map<String, String> createHeaderMatrixMap(final ItemView itemView) {
        final TableDto header = itemView.getHeader();
        final String[][] headerMatrix = header.getMatrix();
        final Map<String, String> headerMatrixMap = new HashMap<>();
        for (final String[] row : headerMatrix) {
            headerMatrixMap.put(row[0], row[1]);
        }
        return headerMatrixMap;
    }

    private void updateDescription(final Item item, final Map<String, String> headerMatrixMap) {
        String[] newParameter = null;
        final Map<String, String> descriptionMap = ItemUtil.toMap(item.getDescription());
        for (final Map.Entry<String, String> entry : headerMatrixMap.entrySet()) {
            final String parameter = entry.getKey();
            final String newValue = entry.getValue();
            final String oldValue = descriptionMap.get(parameter);
            if (oldValue == null) {
                newParameter = new String[]{parameter, newValue};
            } else {
                if (!oldValue.equals(newValue)) {
                    descriptionMap.replace(parameter, newValue);
                }
            }
            if (newParameter != null) {
                descriptionMap.put(newParameter[0], newParameter[1]);
            }

        }
        final String newDescription = ItemUtil.toDescription(descriptionMap);
        item.setDescription(newDescription);
    }

    private void updateName(final Item item, final Map<String, String> headerMatrixMap) {
        final String oldName = item.getName();
        final String newName = headerMatrixMap.get("Name");
        if (newName != null && !newName.equals(oldName)) {
            item.setName(newName);
        }
    }

    public ItemView createItemView(final Item item) {
        final ItemView itemView = new ItemView();
        itemView.setItemId(item.getId());
        itemView.setHeader(createHeader(item));
        itemView.setSelectableData(createSelectableCharacteristics(item));
        itemView.setItems(createTableView(new ArrayList<>(item.getChildItems())));
        itemView.setReplacers(createReplacersTable(item));
        return itemView;
    }

    private TableDto createHeader(final Item item) {
        final List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Name", item.getName()});
        final String tableName = item.getCategory().replace(" (i)", "") + " " + item.getName();
        return createTable(tableName, ItemUtil.toMap(item.getDescription()), list);
    }

    private TableDto createTable(final String tableName,
                                 final Map<String, String> descriptionMap,
                                 final List<String[]> list) {
        for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            list.add(new String[]{entry.getKey(), entry.getValue()});
        }
        return new TableDto(tableName, listToMatrix(list));
    }

    private TableDto createSelectableCharacteristics(final Item item) {
        final String noName = "";
        final List<String[]> list = new ArrayList<>();
        for (final Item info : item.getAdditionalData()) {
            list.add(new String[]{
                    info.getCategory().replace(" (i)", ""),
                    info.getName(),
                    info.getId().toString(),
                    info.getDescription().equals("-") ? "no data" : info.getDescription()});
        }
        return new TableDto(noName, listToMatrix(list));
    }

    private TableDto createReplacersTable(final Item item) {
        final List<String[]> list = getReplacersData(item);
        final String[][] matrix = listToMatrix(list);
        return new TableDto(matrix.length > 0 ? "Replacers" : null, matrix);
    }

    private List<String[]> getReplacersData(final Item item) {
        final List<String[]> data = new ArrayList<>();
        for (final Replacer replacer : item.getReplacers()) {
            data.add(new String[]{
                    replacer.getComment(),
                    createReplacerButtonText(replacer.getItem()),
                    replacer.getItem().getId().toString()
            });
        }
        return data;
    }

    private String createReplacerButtonText(final Item replacer) {
        if (isAddManufacturerName(replacer)) {
            return ItemUtil.getValueFromDescription(replacer.getDescription(), "Manufacturer")
                    + " " + replacer.getName();
        } else {
            return replacer.getName();
        }
    }

    private boolean isAddManufacturerName(final Item replacer) {
        final String category = replacer.getCategory();
        return category.equals("Seal") || category.equals("Spark plug");
    }

    public ItemView motorcycleCatalogue() {
        final List<Item> motorcycles = find("Motorcycle");

        final String tableName = "Motorcycle catalogue";
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Models", String.valueOf(motorcycles.size())});

        final ItemView itemView = new ItemView();
        itemView.setHeader(new TableDto(tableName, listToMatrix(list)));
        itemView.setSelectableData(stubTable());
        final int noMatterWhatNumber = 123;
        final List<TableDto> tables = new ArrayList<>(Collections.singletonList(motorcyclesTable(motorcycles)));
        itemView.setItems(new TableViewDto(noMatterWhatNumber, tables));
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

    public TableDto motorcyclesTable(final List<Item> motorcycles) {
        final String tableName = "Motorcycles";
        final List<String[]> rows = new ArrayList<>();
        for (final Item motorcycle : motorcycles) {
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

//    private List<ItemQuantity> createItemQuantities(final TransitiveItemDescriptionMap descriptionMap) {
//        final List<ItemQuantity> itemQuantities = new ArrayList<>();
//        for (final Map.Entry entry : descriptionMap.getItems().entrySet()) {
//            final String category = entry.getKey().toString();
//            if (entry.getValue().toString().contains(";")) {
//                final String[] names = entry.getValue().toString().split("; ");
//                for (final String name : names) {
//                    final ItemQuantity itemQuantity = createItemQuantity(name,category);
//                    if (itemQuantity != null) {
//                        itemQuantities.add(itemQuantity);
//                    }
//                }
//            } else {
//                final String name = entry.getValue().toString();
//                final ItemQuantity itemQuantity = createItemQuantity(name, category);
//                if (itemQuantity != null) {
//                    itemQuantities.add(itemQuantity);
//                }
//            }
//        }
//
//        return itemQuantities;
//    }

//    private ItemQuantity createItemQuantity(final String value, final String category) {
//        String name;
//        String location = "";
//        String quantity;
//        if (SpecificStringUtil.containsParentheses(value)) {
//            name = SpecificStringUtil.getStringBeforeParentheses(value);
//            String additionalData = SpecificStringUtil.getStringBetweenParentheses(value);
//            location = additionalData.contains(" - ") ? additionalData.split(" - ")[0] : "-";
//            quantity = additionalData.contains(" - ") ? additionalData.split(" - ")[1] : additionalData;
//        } else {
//            name = value;
//            location = "-";
//            quantity = category.equals("Spark plug") ? "2" : "1";
//        }
//        final TransitiveItem child = category.equals("Seal") ? getUssrSealBySize(name) : find(category, name);
//        if (child != null) {
//            final ItemQuantity itemQuantity  = new ItemQuantity();
//            itemQuantity.setItem(child);
//            itemQuantity.setLocation(location);
//            itemQuantity.setQuantity(quantity);
//            return itemQuantity;
//        } else {
//            return null;
//        }
//    }

    private Item createSelectableStub(final String value, final String category) {
        final Item selectableStub = new Item();
        selectableStub.setCategory(category);
        selectableStub.setName(value);
        return selectableStub;
    }

    public Item getUssrSealBySize(final String searchingSize) {
        final List<Item> ussrSeals = filter(find("Seal"), "Manufacturer", "USSR");
        for (Item seal : ussrSeals) {
            final String actualSize = ItemUtil.getValueFromDescription(seal.getDescription(), "Size, mm");
            if (actualSize.equals(searchingSize)) {
                return seal;
            }
        }
        return null;
    }

    private List<Item> filter(final List<Item> items,
                              final String parameter,
                              final String searchingValue) {
        final List<Item> filteredItems = new ArrayList<>();
        for (Item item : items) {
            final String value = ItemUtil.getValueFromDescription(item.getDescription(), parameter);
            if (value != null && value.equals(searchingValue)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public TableViewDto createTableView(final List<ChildItem> childItems) {
        final List<TableDto> tables = new ArrayList<>();
        for (final List<ChildItem> categorizedChildItems : ItemUtil.categorizeChildItems(childItems)) {
            tables.add(createTable(categorizedChildItems));
        }
        return new TableViewDto(childItems.size(), tables);
    }

    public TableDto createTable(final List<ChildItem> childItems) {
        final String tableName = childItems.get(0).getItem().getCategory();
        final List<String[]> rows = new ArrayList<>();
        for (final ChildItem childItem : childItems) {
            final Item item = childItem.getItem();
            final String[] row = {
                    childItem.getLocation(),
                    item.getCategory().equals("Seal")
                            ? ItemUtil.getValueFromDescription(item.getDescription(), "Size, mm")
                            : item.getName(),
                    childItem.getQuantity() != null ? childItem.getQuantity() : "0",
                    item.getId().toString()
            };

            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

    public Item getOrCreate(final TransitiveItem transitiveItem) {
        final Item item = find(transitiveItem.getCategory(), transitiveItem.getName());
        return item != null ? item : create(transitiveItem);
    }

    public Item create(final TransitiveItem transitiveItem) {
        final TransitiveItemDescriptionMap descriptionMap = createDescriptionMap(transitiveItem, transitiveItemService);
        final Item item = new Item();
        item.setName(transitiveItem.getName());
        item.setCategory(transitiveItem.getCategory());
        item.setDescription(createItemDescription(transitiveItem));
        item.getChildItems().addAll(createChildItems(transitiveItem, descriptionMap.getItems()));
        item.getAdditionalData().addAll(createAdditionalDataItems(descriptionMap.getSelectableCharacteristics()));
        item.getReplacers().addAll(createReplacers(transitiveItem));
        return item;
    }

    public String createItemDescription(final TransitiveItem transitiveItem) {
        final TransitiveItemDescriptionMap descriptionMap = createDescriptionMap(transitiveItem, transitiveItemService);
        descriptionMap.getItems().clear();
        descriptionMap.getSelectableCharacteristics().clear();
        return ItemUtil.toDescription(descriptionMap);
    }

    public List<ChildItem> createChildItems(final TransitiveItem parent,
                                            final Map<String, String> childItemsDescription) {
        final List<ChildItem> childItems = new ArrayList<>();
        for (final Map.Entry entry : childItemsDescription.entrySet()) {
            final String category = entry.getKey().toString();
            if (entry.getValue().toString().contains(";")) {
                final String[] names = entry.getValue().toString().split("; ");
                for (final String name : names) {
                    final ChildItem childItem = createChildItem(parent, name, category);
                    if (childItem != null) {
                        childItems.add(childItem);
                    }
                }
            } else {
                final String name = entry.getValue().toString();
                final ChildItem childItem = createChildItem(parent, name, category);
                if (childItem != null) {
                    childItems.add(childItem);
                }
            }
        }

        return childItems;
    }

    public ChildItem createChildItem(final TransitiveItem parent, final String value, final String category) {
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
        final TransitiveItem oldChild = category.equals("Seal")
                ? transitiveItemService.getUssrSealBySize(name)
                : transitiveItemService.find(category, name);

        if (oldChild != null) {
            Item child = getOrCreate(oldChild);

            final ChildItem childItem = new ChildItem();
            childItem.setName(parent.getName() + " - " + name);
            childItem.setItem(child);
            childItem.setLocation(location);
            childItem.setQuantity(quantity);
            return childItem;
        } else {
            return null;
        }
    }

    private List<Replacer> createReplacers(final TransitiveItem transitiveItem) {
        final List<Replacer> replacers = new ArrayList<>();
        final String replacersSourceString = transitiveItem.getReplacer();
        if (replacersSourceString == null || replacersSourceString.equals("-")) {
            return replacers;
        }
        for (final String replacerData : Arrays.asList(replacersSourceString.split("; "))) {
            String replacerName;
            String comment = null;
            if (SpecificStringUtil.containsParentheses(replacerData)) {
                replacerName = SpecificStringUtil.getStringBeforeParentheses(replacerData);
                comment = SpecificStringUtil.getStringBetweenParentheses(replacerData);
            } else {
                replacerName = replacerData;
            }
            final String category = transitiveItem.getCategory();
            final TransitiveItem transitiveReplacerItem = transitiveItemService.find(category, replacerName);
            final Item replacerItem = getOrCreate(transitiveReplacerItem);

            final Replacer replacer = new Replacer();
            replacer.setName(transitiveItem.getName() + " - " + replacerName);
            replacer.setItem(replacerItem);
            replacer.setComment(comment);

            replacers.add(replacer);
        }

        return replacers;
    }

    private List<Item> createAdditionalDataItems(final Map<String, String> selectableCharacteristics) {
        final List<Item> additionalDataItems = new ArrayList<>();
        for (final Map.Entry<String, String> entry : selectableCharacteristics.entrySet()) {
            final String category =  ItemUtil.getInfoCategory(entry.getKey());
            final String name = entry.getValue();
            final TransitiveItem additionalDataItem = transitiveItemService.find(category, name);
            if (additionalDataItem != null) {
                additionalDataItems.add(getOrCreate(additionalDataItem));
            }
        }
        return additionalDataItems;
    }

    public Set<String> findAllInfoCategories() {
        return findInfoCategories(findAllCategories());
    }

    public Set<String> findInfoCategories(final Set<String> categories) {
        final Set<String> infoCategories = new HashSet<>();
        for (final String category : categories) {
            if (category.contains(" (i)")) {
                infoCategories.add(category);
            }
        }
        return infoCategories;
    }

    public Set<String> findAllCategories() {
        return findCategories(findAll());
    }

    public Set<String> findCategories(final List<Item> items) {
        final Set<String> categories = new HashSet<>();
        for (final Item item : items) {
            categories.add(item.getCategory());
        }
        return categories;
    }

}
