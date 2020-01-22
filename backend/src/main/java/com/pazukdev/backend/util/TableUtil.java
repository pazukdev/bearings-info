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
import static com.pazukdev.backend.util.CategoryUtil.Category.Info.MATERIAL;
import static com.pazukdev.backend.util.CategoryUtil.Parameter.INSULATION;
import static com.pazukdev.backend.util.SpecificStringUtil.*;

public class TableUtil {

    public static PartsTable wishListTable(final Set<ChildItem> items, final ItemService itemService) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        for (final ChildItem item : items) {
            final NestedItemDto itemDto = createWishListItem(item, itemService.getUserService());
            dtos.add(itemDto);
        }
        final String[] header = {"Comment", "Name", "Quantity, pcs"};
//        final String[] header = null;
        final Set<String> categories = itemService.findAllCategories();
        return PartsTable.create(header, dtos, categories);
    }

    public static PartsTable specialItemsTable(final List<Item> items, final ItemService itemService) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        for (final Item item : items) {
            final NestedItemDto itemDto = createItemForItemsManagement(item, itemService.getUserService());
            dtos.add(itemDto);
        }
//        final String[] header = {"Category", "Name", "-"};
        final String[] header = null;
        final Set<String> categories = itemService.findAllCategories();
        return PartsTable.create(header, dtos, categories);
    }

    public static  PartsTable motorcyclesTable(final List<Item> motorcycles, final UserService userService) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        final Set<String> categories = new HashSet<>();
        for (final Item motorcycle : motorcycles) {
            final NestedItemDto motorcycleDto = createMotorcycle(motorcycle, userService);
            dtos.add(motorcycleDto);
            categories.add(motorcycleDto.getItemCategory());
        }

        final String[] header = {"Production", "Model", "Manufacturer"};
        return PartsTable.create(header, dtos, categories);
    }

    public static  PartsTable usersTable(final List<UserEntity> users) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        for (final UserEntity user : users) {
            dtos.add(createUser(user));
        }
        final String[] header = {"Role", "Username", "Rating"};
        final Set<String> partCategories = new HashSet<>(Arrays.asList("Admin", "User"));
        return PartsTable.create(header, dtos, partCategories);
    }

    public static PartsTable createPartsTable(final Item item, final ItemService service) {
        return createPartsTable(item, service, getDefaultPartsHeader(), false);
    }

    public static PartsTable createPartsSummaryTable(final Item item, final ItemService service) {
        final String[] header = getDefaultPartsHeader();
        header[0] = "-";
        return createPartsTable(item, service, header, true);
    }

    private static String[] getDefaultPartsHeader() {
        return new String[]{"Location", "Partnumber", "Pcs/Vol"};
    }

    private static PartsTable createPartsTable(final Item item,
                                               final ItemService service,
                                               final String[] header,
                                               final boolean summary) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        addParts(item.getChildItems(), dtos, service.getUserService(), summary, null);
        final Set<String> categories = service.findAllPartCategories();
        return PartsTable.create(header, dtos, categories);
    }

    private static void addParts(final Set<ChildItem> parts,
                                 final List<NestedItemDto> dtos,
                                 final UserService userService,
                                 final boolean summary,
                                 final Double parentQuantity) {
        for (final ChildItem part : parts) {
            boolean add = true;
            final NestedItemDto partDto = createChildItem(part, userService, !summary);
            Double quantity = null;
            if (summary) {
                quantity = getFirstNumber(part.getQuantity());
                if (quantity != null && parentQuantity != null) {
                    quantity = quantity * parentQuantity;
                    partDto.setSecondComment(doubleToString(quantity));
                }
                for (final NestedItemDto dto : dtos) {
                    final boolean itemIsInList = dto.getItemId().equals(partDto.getItemId());
                    if (itemIsInList) {
                        final String totalQuantity = sumQuantities(dto.getSecondComment(), doubleToString(quantity));
                        dto.setSecondComment(totalQuantity);
                        add = false;
                        break;
                    }
                }
            }
            if (add) {
                dtos.add(partDto);
            }
            if (summary) {
                addParts(part.getItem().getChildItems(), dtos, userService, true, quantity);
            }
        }
    }

    public static ReplacersTable createReplacersTable(final Item item, final UserService userService) {
        final ReplacersTable replacersTable = new ReplacersTable();
        replacersTable.setName("Replacers");
        final List<Replacer> replacers = new ArrayList<>(item.getReplacers());
        for (final Replacer replacer : replacers) {
            final NestedItemDto replacerDto = createReplacer(replacer, userService);
            replacersTable.getReplacers().add(replacerDto);
        }
        return replacersTable;
    }

    public static HeaderTable createHeader(final Item item, final ItemService itemService) {
        final String itemName = item.getName();
        final String itemCategory = item.getCategory();
        final String tableName = getHeaderTableName(itemCategory, itemName);
        final Map<String, String> description = ItemUtil.toMap(item.getDescription());

        final List<HeaderTableRow> headerTableRows = new ArrayList<>();
        headerTableRows.add(HeaderTableRow.create("Name", itemName));
        return createTable(tableName, description, headerTableRows, itemService);
    }

    public static String getHeaderTableName(final String itemCategory, final String itemName) {
        return itemCategory + " " + itemName;
    }

    private static HeaderTable createTable(final String tableName,
                                           final Map<String, String> descriptionMap,
                                           final List<HeaderTableRow> headerTableRows,
                                           final ItemService itemService) {
        for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            String parameter = entry.getKey();
            String value = entry.getValue();
            String itemId = "-";

            // str.matches(".*\\d
            String category = parameter.replaceAll("[0-9]","").trim();
            if (category.equalsIgnoreCase(INSULATION)) {
                category = MATERIAL;
            }
            final Item foundItem = itemService.find(category, value);
            if (foundItem != null) {
                itemId = foundItem.getId().toString();
            }

            final HeaderTableRow row = HeaderTableRow.create(parameter, value);
            row.setItemId(itemId);
            headerTableRows.add(row);
        }
        return HeaderTable.create(tableName, headerTableRows);
    }

    public static Map<String, String> createHeaderMap(final HeaderTable header) {
        final Map<String, String> map = new HashMap<>();
        for (final HeaderTableRow row : header.getRows()) {
            map.put(row.getParameter(), row.getValue());
        }
        return map;
    }

    private static boolean isBoxer(final Item motorcycle) {
        final String description = motorcycle.getDescription().toLowerCase();
        final String type = ItemUtil.getValueFromDescription(description, "type");
        final String manufacturer = ItemUtil.getValueFromDescription(description, "manufacturer");
        final String name = motorcycle.getName().toLowerCase();

        if (type != null && type.equals("boxer")) {
            return true;
        } else if (manufacturer != null) {
            if (manufacturer.contains("imz") || manufacturer.contains("kmz")
                    || (manufacturer.equals("bmw") && name.equals("r75"))
                    || (manufacturer.equals("zundapp") && name.equals("ks750"))) {
                return true;
            }
        }
        return false;
    }

}
