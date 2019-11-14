package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.ItemView;
import com.pazukdev.backend.dto.NestedItemDto;
import com.pazukdev.backend.dto.table.PartsTable;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
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
            return stubPartsTable();
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

    public static ReplacersTable stubReplacersTable() {
        final ReplacersTable replacersTable = new ReplacersTable();
        replacersTable.setName("stub");
        return replacersTable;
    }

    public static TableDto stubTable() {
        return new TableDto("stub", new String[][]{{""}});
    }

    public static TableViewDto createTableView(final List<ChildItem> childItems, final String language) {
        final List<TableDto> tables = new ArrayList<>();
        for (final List<ChildItem> categorizedChildItems : ItemUtil.categorizeChildItems(childItems)) {
            tables.add(createTable(categorizedChildItems, language));
        }
        return new TableViewDto(childItems.size(), tables);
    }

    public static TableDto createHeader(final Item item,
                                        final String itemCategoryInUserLanguage,
                                        final String language,
                                        final ItemService itemService) {
//        final String nameParameter = translateFromEnglish("Name", language);
        final String nameParameter = "Name";
//        final String itemName = translateFromEnglish(item.getName(), language);
        final String itemName = item.getName();
        final String tableName = itemCategoryInUserLanguage + " " + itemName;

        final List<String[]> list = new ArrayList<>();
        list.add(new String[]{nameParameter, itemName});
        return createTable(tableName, ItemUtil.toMap(item.getDescription()), list, language, itemService);
    }

    public static String[][] listToMatrix(List<String[]> list) {
        int j = 0;
        String[][] matrix = new String[list.size()][];
        for (String[] s : list) {
            matrix[j++] = s;
        }
        return matrix;
    }

    public static Map<String, String> createHeaderMatrixMap(final ItemView itemView) {
        final TableDto header = itemView.getHeader();
        final String[][] headerMatrix = header.getMatrix();
        final Map<String, String> headerMatrixMap = new HashMap<>();
        for (final String[] row : headerMatrix) {
            headerMatrixMap.put(row[0], row[1]);
        }
        return headerMatrixMap;
    }

    private static PartsTable stubPartsTable() {
        final PartsTable partsTable = new PartsTable();
        partsTable.setName("stub");
        return partsTable;
    }

    private static TableDto createTable(final String tableName,
                                        final Map<String, String> descriptionMap,
                                        final List<String[]> list,
                                        final String language,
                                        final ItemService itemService) {
        for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            String parameter = entry.getKey();
            String value = entry.getValue();
            String itemId = "no id";
            String message = "";
            final Item foundItem = itemService.find(parameter, value);
            if (foundItem != null) {
                itemId = foundItem.getId().toString();
                message = "show button";
            }
//            parameter = translateFromEnglish(parameter, language);
//            value = translateFromEnglish(value, language);
            list.add(new String[]{parameter, value, itemId, message});
        }
        return new TableDto(tableName, listToMatrix(list));
    }

    private static TableDto createTable(final List<ChildItem> childItems, final String language) {
        String tableName = childItems.get(0).getItem().getCategory();
//        tableName = translateFromEnglish(tableName, language);

        final List<String[]> rows = new ArrayList<>();
        for (final ChildItem childItem : childItems) {
            final Item item = childItem.getItem();

            String location = childItem.getLocation();
            String buttonText = getButtonTextForPartsTable(item);
            String quantityData = getQuantityData(childItem.getQuantity());
            String itemId = item.getId().toString();

//            location = translateFromEnglish(location, language);
//            buttonText = translateFromEnglish(buttonText, language);
//            quantityData = translateFromEnglish(quantityData, language);

            final String[] row = {location, buttonText, quantityData, itemId};
            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

    private static String getButtonTextForPartsTable(final Item item) {
        return item.getCategory().equals("Seal")
                ? ItemUtil.getValueFromDescription(item.getDescription(), "Size, mm")
                : item.getName();
    }

    private static String getQuantityData(final String childItemQuantity) {
        return childItemQuantity != null ? childItemQuantity : "0";
    }

}
