package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.NestedItemDto;
import com.pazukdev.backend.dto.table.HeaderTable;
import com.pazukdev.backend.dto.table.HeaderTableRow;
import com.pazukdev.backend.dto.table.PartsTable;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.entity.ChildItem;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.Replacer;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.UserService;

import java.util.*;

import static com.pazukdev.backend.dto.factory.NestedItemDtoFactory.*;

public class TableUtil {

    public static PartsTable specialItemsTable(final List<Item> items,
                                               final String tableName,
                                               final String language,
                                               final ItemService itemService) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        for (final Item item : items) {
            final NestedItemDto itemDto = createBasicSpecialNestedItemDto(item, itemService.getUserService());
            dtos.add(itemDto);
        }
//        final String[] header = {"Category", "Name", "-"};
        final String[] header = null;
        final Set<String> categories = itemService.findAllCategories();
        return PartsTable.create(dtos, tableName, header, categories, language);
    }

    public static  PartsTable motorcyclesTable(final List<Item> motorcycles,
                                               final String tableName,
                                               final String language,
                                               final UserService userService) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        final Set<String> categories = new HashSet<>();
        for (final Item motorcycle : motorcycles) {
            final NestedItemDto motorcycleDto = createMotorcycle(motorcycle, userService);
            dtos.add(motorcycleDto);
            categories.add(motorcycleDto.getItemCategory());
        }

        final String[] header = {"Production", "Model", "Manufacturer"};
        return PartsTable.create(dtos, tableName, header, categories, language);
    }

    public static  PartsTable usersTable(final List<UserEntity> users,
                                         final String tableName,
                                         final String language) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        for (final UserEntity user : users) {
            dtos.add(createUser(user));
        }
        final String[] header = {"Role", "Username", "Rating"};
        final Set<String> categories = new HashSet<>(Arrays.asList("Admin", "User"));
        return PartsTable.create(dtos, tableName, header, categories, language);
    }

    public static PartsTable createPartsTable(final Item item,
                                              final String tableName,
                                              final String language,
                                              final ItemService itemService) {
        if (!CategoryUtil.itemIsAbleToContainParts(item)) {
            return PartsTable.createStub();
        }
        final List<NestedItemDto> dtos = new ArrayList<>();
        final List<ChildItem> parts = new ArrayList<>(item.getChildItems());
        for (final ChildItem part : parts) {
            final NestedItemDto partDto = createChildItem(part, itemService.getUserService());
            dtos.add(partDto);
        }
        final String[] header = {"Location", "Partnumber", "Pcs/Vol"};
        //final String[] header = null;
        final Set<String> categories = itemService.findAllPartCategories();
        return PartsTable.create(dtos, tableName, header, categories, language);
    }

    public static ReplacersTable createReplacersTable(final Item item, final UserService userService) {
        final ReplacersTable replacersTable = new ReplacersTable();
        replacersTable.setName("Replacers");
        final List<Replacer> replacers = new ArrayList<>(item.getReplacers());
        for (final Replacer replacer : replacers) {
            final NestedItemDto replacerDto = createReplacer(replacer, userService);
            replacersTable.getReplacers().add(replacerDto);
        }
        replacersTable.getReplacers().sort(Comparator.comparing(NestedItemDto::getRating).reversed());
        return replacersTable;
    }

//    public static TableViewDto createTableView(final List<ChildItem> childItems, final String language) {
//        final List<HeaderTable> tables = new ArrayList<>();
//        for (final List<ChildItem> categorizedChildItems : ItemUtil.categorizeChildItems(childItems)) {
//            tables.add(createTable(categorizedChildItems, language));
//        }
//        return new TableViewDto(childItems.size(), tables);
//    }

    public static HeaderTable createHeader(final Item item,
                                           final String itemCategoryInUserLanguage,
                                           final ItemService itemService) {
        final String nameParameter = "Name";
        final String itemName = item.getName();
        final String tableName = itemCategoryInUserLanguage + " " + itemName;

        final List<HeaderTableRow> rows = ItemDescriptionUtil.toHeaderRows(item.getDescription());
        rows.add(HeaderTableRow.create(nameParameter, itemName, "-"));
        return createTable(tableName, rows, itemService);
    }

    private static HeaderTable createTable(final String tableName,
                                           final List<HeaderTableRow> rows,
                                           final ItemService itemService) {
        for (final HeaderTableRow row : rows) {
            row.setItemId("no id");
            final Item foundItem = itemService.find(row.getParameter(), row.getValue());
            if (foundItem != null) {
                row.setItemId(foundItem.getId().toString());
                row.setMessage("show button");
            }
        }
        return HeaderTable.create(tableName, rows);
    }

}
