package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.NestedItemDto;
import com.pazukdev.backend.dto.table.HeaderTable;
import com.pazukdev.backend.dto.table.HeaderTableRow;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.Replacer;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pazukdev.backend.dto.factory.NestedItemDtoFactory.createReplacer;
import static com.pazukdev.backend.util.CategoryUtil.Category.MATERIAL;
import static com.pazukdev.backend.util.CategoryUtil.Parameter.INSULATION;
import static com.pazukdev.backend.util.ItemUtil.*;

public class TableUtil {

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
        final Map<String, String> description = toMap(item.getDescription());

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
            String link = null;

            // str.matches(".*\\d
            String category = parameter.split(MULTI_PARAM_SEPARATOR)[0];
            if (category.equalsIgnoreCase(INSULATION)) {
                category = MATERIAL;
            }
            final Item foundItem = itemService.find(category, value);
            if (foundItem != null) {
                itemId = foundItem.getId().toString();
                link = foundItem.getWiki();
            }

            final HeaderTableRow row = HeaderTableRow.create(parameter, value);
            row.setItemId(itemId);
            row.setLink(link);
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
        final String type = getValueFromDescription(description, "type");
        final String manufacturer = getValueFromDescription(description, "manufacturer");
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
