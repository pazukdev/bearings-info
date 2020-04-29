package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.table.HeaderTable;
import com.pazukdev.backend.dto.table.HeaderTableRow;
import com.pazukdev.backend.entity.Childable;
import com.pazukdev.backend.entity.Item;

import java.util.List;
import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 */
public class MessageUtil {

//    public static void addMessage(final String message,
//                                  final List<String> messages,
//                                  final Long itemId,
//                                  final String itemName) {
//        messages.add(getItemInfo(itemId, itemName) + message);
//    }

    public static void addMessage(final String message, final List<String> messages) {
        messages.add(message);
    }

    public static void addChildItemMessage(final Childable childable,
                                           final String actionType,
                                           final List<String> messages) {
        final String message = childable.getItem()
                + actionType + "(e)d"
                + " as " + childable.getType() + " (" + childable.getDetails() + ")";
        addMessage(message, messages);
    }

    public static void addItemDescriptionMessage(final HeaderTable header,
                                                 final Item oldItem,
                                                 final List<String> messages) {
        final Map<String, String> map = ItemUtil.toMap(oldItem.getDescription());
        map.put("Name", oldItem.getName());
        map.put("Category", oldItem.getCategory());

        final String itemInfo = oldItem + ": ";

        for (final HeaderTableRow row : header.getRows()) {
            final String param = row.getParameter();
            final String value = row.getValue();
            final boolean newRow = row.getId() == null;
            String message = null;
            if (newRow) {
                message = "new param=" + param + " value=" + value + " added";
            } else {
                final String oldValue = map.get(param);
                if (oldValue == null) {
                    message = "param name changed to " + param
                            + " (value=" + value + ")";
                } else if (!oldValue.equals(value)) {
                    message = "param " + param
                            + ": value changed from " + oldValue
                            + " to " + value;
                }
            }
            if (message != null) {
                messages.add(itemInfo + message);
            }
        }

        for (final Map.Entry<String, String> entry : map.entrySet()) {
            boolean deleted = true;
            for (final HeaderTableRow row : header.getRows()) {
                if (row.getName().equals(entry.getKey())) {
                    deleted = false;
                    break;
                }
            }
            if (deleted) {
                final String message = "param " + entry.getKey() + " removed";
                messages.add(itemInfo + message);
            }
        }
    }

    public static String toString(final List<String> list) {
        String result = "";
        for (final String s : list) {
            result += s + "\n";
        }
        return result;
    }

}
