package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.dto.item.ItemDescriptionMap;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.dto.item.ItemQuantity;
import com.pazukdev.backend.dto.table.ItemView;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.repository.ItemRepository;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.SpecificStringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ItemService extends AbstractService<ItemEntity, ItemDto> {

    public ItemService(final ItemRepository repository, final ItemConverter converter) {
        super(repository, converter);
    }

    @Transactional
    public ItemView getItem(final Long id) throws EntityExistsException {
        return createItemView(getOne(id));
    }

    public ItemDescriptionMap createDescriptionMap(final ItemEntity item) {
        return ItemUtil.createDescriptionMap(item, this);
    }

    public ItemView createItemView(final ItemEntity item) {
        final ItemView itemView = new ItemView();
        final String tableName = item.getCategory() + " " + item.getName();
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Name", item.getName()});
        final Integer quantity = item.getQuantity();
        if (quantity != null && quantity > 0) {
            list.add(new String[]{"Quantity",  quantity.toString()});
        }
        final ItemDescriptionMap descriptionMap = createDescriptionMap(item);
        for (final Map.Entry<String, String> entry : descriptionMap.getCharacteristics().entrySet()) {
            list.add(new String[]{entry.getKey(), entry.getValue()});
        }

        String[][] matrix = listToMatrix(list);
        itemView.setHeader(new TableDto(tableName, matrix));

        itemView.setItems(createTableView(descriptionMap));

        list = new ArrayList<>();
        final List<String[]> replacersData = ItemUtil.parseReplacersSourceString(item.getReplacer());
        for (final String[] replacerData : replacersData) {
            final ItemEntity replacer = find(item.getCategory(), replacerData[0]);
            if (replacer != null) {
                list.add(new String[]{
                        replacerData[1],
                        (replacer.getCategory().equals("Seal")
                                ? (ItemUtil.getValueFromDescription(replacer, "Manufacturer") + " ")
                                : "") + replacer.getName(),
                        replacer.getId().toString()});
            }
        }

        matrix = listToMatrix(list);
        itemView.setReplacers(new TableDto(matrix.length > 0 ? "Replacers" : null, matrix));

        return itemView;
    }

    public ItemView motorcycleCatalogue() {
        final List<ItemEntity> motorcycles = find("Motorcycle");

        final String tableName = "Motorcycle catalogue";
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Models", String.valueOf(motorcycles.size())});

        final ItemView itemView = new ItemView();
        itemView.setHeader(new TableDto(tableName, listToMatrix(list)));
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

    public TableDto motorcyclesTable(final List<ItemEntity> motorcycles) {
        final String tableName = "Motorcycles";
        final List<String[]> rows = new ArrayList<>();
        for (final ItemEntity motorcycle : motorcycles) {
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
                    final ItemQuantity itemQuantity = createItemQuantity(descriptionMap.getParent(), name,category);
                    if (itemQuantity != null) {
                        itemQuantities.add(itemQuantity);
                    }
                }
            } else {
                final String name = entry.getValue().toString();
                final ItemQuantity itemQuantity = createItemQuantity(descriptionMap.getParent(), name, category);
                if (itemQuantity != null) {
                    itemQuantities.add(itemQuantity);
                }
            }
        }
        return itemQuantities;
    }

    private ItemQuantity createItemQuantity(final ItemEntity parentItem, final String value, final String category) {
        String name;
        String location = "";
        Integer quantity;
        if (SpecificStringUtil.containsParentheses(value)) {
            name = SpecificStringUtil.getStringBeforeParentheses(value);
            String additionalData = SpecificStringUtil.getStringBetweenParentheses(value);
            location = additionalData.contains(" - ") ? additionalData.split(" - ")[0] : "-";
            quantity = additionalData.contains(" - ") ?
                    Integer.valueOf(additionalData.split(" - ")[1]) : Integer.valueOf(additionalData);
        } else {
            name = value;
            location = "-";
            quantity = category.equals("Spark plug") ? 2 : 1;
        }
        final ItemEntity child = category.equals("Seal") ? getUssrSealBySize(name) : find(category, name);
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

    private ItemEntity getUssrSealBySize(final String searchingSize) {
        final List<ItemEntity> ussrSeals = filterUssrMade(find("Seal"));
        for (ItemEntity seal : ussrSeals) {
            final String actualSize = ItemUtil.getValueFromDescription(seal.getDescription(), "Size, mm");
            if (actualSize.equals(searchingSize)) {
                return seal;
            }
        }
        return null;
    }

    private List<ItemEntity> filterUssrMade(final List<ItemEntity> items) {
        final List<ItemEntity> filteredItems = new ArrayList<>();
        for (ItemEntity item : items) {
            final String manufacturer = ItemUtil.getValueFromDescription(item.getDescription(), "Manufacturer");
            if (manufacturer != null && manufacturer.equals("USSR")) {
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

//    public TableDto createTable(final String itemName) {
//        return createTable(getItems(itemName));
//    }

    public TableDto createTable(final List<ItemQuantity> itemQuantities) {
        final String tableName = itemQuantities.get(0).getItem().getCategory();
        final List<String[]> rows = new ArrayList<>();
        for (final ItemQuantity itemQuantity : itemQuantities) {
            final ItemEntity item = itemQuantity.getItem();
            final String[] row = {
                    itemQuantity.getLocation(),
                    item.getCategory().equals("Seal") ?
                            ItemUtil.getValueFromDescription(item, "Size, mm") : item.getName(),
                    itemQuantity.getQuantity() != null ? itemQuantity.getQuantity().toString() : "0",
                    item.getId().toString()
            };

            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

//    public List<ItemQuantity> getItems(final String itemName) {
//        final ItemEntity item = findByName(itemName);
//        return createItemQuantities(item);
//    }

    @Transactional
    @Override
    public ItemEntity findByName(final String name) {
        return ((ItemRepository) repository).findByName(name);
    }

    @Transactional
    public List<ItemEntity> find(final String category, final String... names) {
        final List<ItemEntity> items = new ArrayList<>();
        for (final String name : names) {
            items.add(find(category, name));
        }
        return items;
    }

    @Transactional
    public ItemEntity find(final String category, final String name) {
        for (final ItemEntity item : find(category)) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Transactional
    public List<ItemEntity> find(final String category) {
        final List<ItemEntity> categorizedItems = new ArrayList<>();
        for (final ItemEntity item : findAll()) {
            if (item.getCategory().toLowerCase().equals(category.toLowerCase())) {
                categorizedItems.add(item);
            }
        }
        return categorizedItems;
    }

}




















