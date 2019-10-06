package com.pazukdev.backend.util;

import com.pazukdev.backend.converter.ReplacerConverter;
import com.pazukdev.backend.dto.item.NestedItemDto;
import com.pazukdev.backend.dto.item.NestedItemDtoFactory;
import com.pazukdev.backend.dto.table.PartsTable;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import com.pazukdev.backend.entity.item.ChildItem;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.Replacer;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableUtil {

    public static PartsTable specialItemsTable(final List<Item> items,
                                               final ItemService itemService,
                                               final UserService userService) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        for (final Item item : items) {
            dtos.add(NestedItemDtoFactory.createBasicSpecialNestedItemDto(item, userService));
        }
        return PartsTable.create(dtos, itemService.findAllCategories());
    }

    public static PartsTable createPartsTable(final Item item,
                                              final String name,
                                              final ItemService itemService,
                                              final UserService userService) {
        if (!CategoryUtil.itemIsAbleToContainParts(item)) {
            return stubPartsTable();
        }
        final PartsTable partsTable = new PartsTable();
        partsTable.setName(name);
        final List<ChildItem> parts = new ArrayList<>(item.getChildItems());
        for (final ChildItem part : parts) {
            partsTable.getParts().add(NestedItemDtoFactory.createPart(part, userService));
        }
        return PartsTable.create(partsTable.getParts(), itemService.findAllPartCategories());
    }

    public static PartsTable stubPartsTable() {
        final PartsTable partsTable = new PartsTable();
        partsTable.setName("stub");
        return partsTable;
    }

    public static ReplacersTable stubReplacersTable() {
        final ReplacersTable replacersTable = new ReplacersTable();
        replacersTable.setName("stub");
        return replacersTable;
    }

    public static TableDto stubTable() {
        return new TableDto("stub", new String[][]{{""}});
    }

    public static ReplacersTable createReplacersTable(final Item item,
                                                      final ReplacerConverter replacerConverter) {
        final ReplacersTable replacersTable = new ReplacersTable();
        replacersTable.setName("Replacers");
        final List<Replacer> replacers = new ArrayList<>(item.getReplacers());
        for (final Replacer replacer : replacers) {
            final String buttonText = ItemUtil.createButtonText(replacer.getItem());

            final NestedItemDto replacerDto = replacerConverter.convertToDto(replacer, buttonText);
            replacerDto.setItemCategory(replacer.getItem().getCategory());
            replacerDto.setStatus(replacer.getItem().getStatus());

            replacersTable.getReplacers().add(replacerDto);
        }
        return replacersTable;
    }

    public static TableDto createTable(final String tableName,
                                       final Map<String, String> descriptionMap,
                                       final List<String[]> list,
                                       final ItemService itemService) {
        for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            final String parameter = entry.getKey();
            final String value = entry.getValue();
            String itemId = "no id";
            String message = "";
            final Item foundItem = itemService.find(parameter, value);
            if (foundItem != null) {
                itemId = foundItem.getId().toString();
                message = "show button";
            }
            list.add(new String[]{parameter, value, itemId, message});
        }
        return new TableDto(tableName, listToMatrix(list));
    }

    public static  PartsTable motorcyclesTable(final List<Item> motorcycles,
                                               final ItemService itemService,
                                               final UserService userService) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        for (final Item item : motorcycles) {
            final String buttonText = ItemUtil.createButtonText(item);

            final NestedItemDto dto = new NestedItemDto();
            dto.setItemId(item.getId());
            dto.setItemName(item.getName());
            dto.setItemCategory(item.getCategory());
            dto.setButtonText(buttonText);
            dto.setLocation(ItemUtil.getValueFromDescription(item.getDescription(), "Production"));
            dto.setStatus(item.getStatus());
            dto.setCreatorName(UserUtil.getCreatorName(item, userService));

            dtos.add(dto);
        }
        return PartsTable.create(dtos, itemService.findAllCategories());
    }

    public static TableViewDto createTableView(final List<ChildItem> childItems,
                                               final ItemService itemService) {
        final List<TableDto> tables = new ArrayList<>();
        for (final List<ChildItem> categorizedChildItems : ItemUtil.categorizeChildItems(childItems)) {
            tables.add(createTable(categorizedChildItems));
        }
        return new TableViewDto(childItems.size(), tables);
    }

    public static TableDto createTable(final List<ChildItem> childItems) {
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

    public static TableDto createHeader(final Item item, final ItemService itemService) {
        final List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Name", item.getName()});
        final String tableName = item.getCategory().replace(" (i)", "") + " " + item.getName();
        return createTable(tableName, ItemUtil.toMap(item.getDescription()), list, itemService);
    }

    public static String[][] listToMatrix(List<String[]> list) {
        int j = 0;
        String[][] matrix = new String[list.size()][];
        for (String[] s : list) {
            matrix[j++] = s;
        }
        return matrix;
    }

}
