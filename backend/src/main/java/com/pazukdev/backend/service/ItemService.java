package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.dto.table.ItemView;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.entity.item.ItemQuantity;
import com.pazukdev.backend.repository.ItemQuantityRepository;
import com.pazukdev.backend.repository.ItemRepository;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.SpecificStringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ItemService extends AbstractService<ItemEntity, ItemDto> {

    private final ItemQuantityRepository itemQuantityRepository;

    public ItemService(final ItemRepository repository,
                       final ItemConverter converter,
                       final ItemQuantityRepository itemQuantityRepository) {
        super(repository, converter);
        this.itemQuantityRepository = itemQuantityRepository;
    }

    @Transactional
    public ItemView getItem(final Long id) throws EntityExistsException {
        return createItemView(getOne(id));
    }

    public ItemView createItemView(final ItemEntity item) {
        final ItemView itemView = new ItemView();
        final String tableName = SpecificStringUtil.capitalize(item.getCategory()) + " " + item.getName();
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Name", item.getName()});
        final Integer quantity = item.getQuantity();
        if (quantity != null && quantity > 0) {
            list.add(new String[]{"Quantity",  quantity.toString()});
        }
        for (Map.Entry entry : ItemUtil.toMap(item.getDescription()).entrySet()) {
            final String value = entry.getValue().toString();
            if (!value.contains(";") && findByName(value) == null) {
                list.add(new String[]{
                        SpecificStringUtil.capitalize(entry.getKey().toString()),
                        entry.getValue().toString()});
            }
        }
        int j = 0;
        String[][] matrix = new String[list.size()][];
        for (String[] s : list) {
            matrix[j++] = s;
        }
        itemView.setHeader(new TableDto(tableName, matrix));

        itemView.setItems(createTableView(item));

        list = new ArrayList<>();
        final String[] replacerNames = item.getReplacer().replaceAll(" ", "").split(";");
        final List<ItemEntity> replacers = new ArrayList<>();
        for (final String name : replacerNames) {
            final ItemEntity replacer = findByName(name);
            if (replacer != null) {
                replacers.add(replacer);
            }
        }
        int i = 0;
        for (final ItemEntity replacer : replacers) {
            list.add(new String[]{"Replacer #" + ++i, replacer.getName(), replacer.getId().toString()});
        }
        int k = 0;
        matrix = new String[list.size()][];
        for (String[] s : list) {
            matrix[k++] = s;
        }
        itemView.setReplacers(new TableDto(matrix.length > 0 ? "Replacers" : null, matrix));

        return itemView;
    }

    public TableViewDto createTableView(final ItemEntity parentItem) {
//        final List<ItemEntity> items = new ArrayList<>();
//        for (Map.Entry entry : ItemUtil.toMap(parentItem.getDescription()).entrySet()) {
//            final String value = entry.getValue().toString();
//            if (value.contains(";")) {
//                final String[] names = value.replaceAll(" ", "").split(";");
//                for (final String name : names) {
//                    final ItemEntity childItem = findByName(name);
//                    if (childItem != null) {
//                        items.add(childItem);
//                    }
//                }
//            } else {
//                final ItemEntity childItem = findByName(value);
//                if (childItem != null) {
//                    items.add(childItem);
//                }
//            }
//        }

        return createTableView(parentItem.getName(), new ArrayList<>(parentItem.getItemQuantities()));
    }

    public TableViewDto createTableView(final String parentItemName, final List<ItemQuantity> quantities) {

        final List<TableDto> tables = new ArrayList<>();
        for (final List<ItemQuantity> categorizedItems : ItemUtil.categorizeItemQuantities(quantities)) {
            tables.add(createTable(categorizedItems));
        }
        return new TableViewDto(quantities.size(), tables);
    }

    public TableDto createTable(final String motorcycleName) {
        return createTable(getItems(motorcycleName));
    }

    public TableDto createTable(final List<ItemQuantity> itemQuantities) {
        final String tableName = itemQuantities.get(0).getItem().getCategory();
        final List<String[]> rows = new ArrayList<>();
        for (final ItemQuantity itemQuantity : itemQuantities) {
            final ItemEntity item = itemQuantity.getItem();
            final String[] row = {
                    item.getCategory(),
                    item.getName(),
                    itemQuantity.getQuantity() != null ? itemQuantity.getQuantity().toString() : "0",
                    item.getId().toString()};
            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

    public List<ItemQuantity> getItems(final String motorcycleName) {
        final ItemEntity item = findByName(motorcycleName);
        return new ArrayList<>(item.getItemQuantities());
    }

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
            if (item.getCategory().equals(category)) {
                categorizedItems.add(item);
            }
        }
        return categorizedItems;
    }

}




















