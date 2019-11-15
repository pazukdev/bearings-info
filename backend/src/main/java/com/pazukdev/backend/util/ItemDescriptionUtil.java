package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.table.HeaderTableRow;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.service.ItemService;

import java.util.ArrayList;
import java.util.List;

public class ItemDescriptionUtil {

    public static List<HeaderTableRow> toHeaderRows(final String description) {
        final List<HeaderTableRow> rows = new ArrayList<>();
        if (description == null || !description.contains(":")) {
            return rows;
        }
        final String[] descriptionAsArray = description.split(";;");
        for (final String s : descriptionAsArray) {
            final String parameter = s.split(":")[0];
            final String value = s.split(":")[1];
            final String uuid = s.split(":")[2];
            final HeaderTableRow row = HeaderTableRow.create(parameter, value, uuid);

            rows.add(row);
        }
        return rows;
    }

    public static HeaderTableRow getHeaderTableRow(final List<HeaderTableRow> rows, final String uuid) {
        for (final HeaderTableRow row : rows) {
            if (row.getUuid().equals(uuid)) {
                return row;
            }
        }
        return null;
    }

    public static HeaderTableRow getRowByParameter(final List<HeaderTableRow> rows, final String parameter) {
        for (final HeaderTableRow row : rows) {
            if (row.getParameter().toLowerCase().equals(parameter.toLowerCase())) {
                return row;
            }
        }
        return null;
    }

    public static String getValue(final List<HeaderTableRow> rows, final String parameter) {
        for (final HeaderTableRow row : rows) {
            if (row.getParameter().toLowerCase().equals(parameter.toLowerCase())) {
                return row.getValue();
            }
        }
        return null;
    }

    public static String toDescription(final List<HeaderTableRow> rows) {
        String description = "";
        for (final HeaderTableRow row : rows) {
            if (row.getParameter().toLowerCase().equals("name")) {
                continue;
            }

            description += row.getParameter() + ":" + row.getValue() + ":" + row.getUuid() + ";;";
        }
        return description;
    }

    public static String createEmptyDescription(final String category, final ItemService itemService) {
        final List<Item> items = itemService.find(category);
        if (items.isEmpty()) {
            return "";
        }
        final List<HeaderTableRow> rows = toHeaderRows(items.get(0).getDescription());
        for (final HeaderTableRow row : rows) {
            row.setValue("-");
        }
        return toDescription(rows);
    }

}
