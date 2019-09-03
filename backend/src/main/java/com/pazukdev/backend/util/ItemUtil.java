package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.entity.item.ItemQuantity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ItemUtil {

    public static void sort(final List<ItemEntity> items) {
        items.sort(Comparator.comparing(ItemEntity::getCategory));
    }

    public static Set<String> getCategories(final List<ItemEntity> items) {
        final Set<String> categories = new HashSet<>();
        for (final ItemEntity item : items) {
            if (item.getCategory() == null) {
                item.setCategory("-");
            }
            categories.add(item.getCategory());
        }
        return categories;
    }

    public static Set<String> getItemQuantityCategories(final List<ItemQuantity> items) {
        final Set<String> categories = new HashSet<>();
        for (final ItemQuantity item : items) {
            if (item.getItem().getCategory() == null) {
                item.getItem().setCategory("-");
            }
            categories.add(item.getItem().getCategory());
        }
        return categories;
    }

    public static List<List<ItemEntity>> categorize(final List<ItemEntity> items) {
        final List<List<ItemEntity>> categorizedItems = new ArrayList<>();
        for (final String category : getCategories(items)) {
            categorizedItems.add(items.stream()
                    .filter(item -> item.getCategory().equals(category)).collect(Collectors.toList()));

        }
        return categorizedItems;
    }

    public static List<List<ItemQuantity>> categorizeItemQuantities(final List<ItemQuantity> items) {
        final List<List<ItemQuantity>> categorizedItems = new ArrayList<>();
        for (final String category : getItemQuantityCategories(items)) {
            categorizedItems.add(items.stream()
                    .filter(item -> item.getItem().getCategory().equals(category)).collect(Collectors.toList()));

        }
        return categorizedItems;
    }

    public static String getValueFromDescription(final String description, final String key) {
        return toMap(description).get(key);
    }

    public static Map<String, String> toMap(final String description) {
        final List<String> descriptionList = Arrays.asList(description.split(";;"));
        final Map<String, String> map = new HashMap<>();
        for (final String element : descriptionList) {
            map.put(element.split(":")[0], element.split(":")[1]);
        }
        return map;
    }

    public static String[] parseReplacerData(final String replacerData) {
        String replacerName;
        String comment = "-";
        if (SpecificStringUtil.containsParentheses(replacerData)) {
            replacerName = SpecificStringUtil.getStringBeforeParentheses(replacerData);
            comment = SpecificStringUtil.getStringBetweenParentheses(replacerData);
        } else {
            replacerName = replacerData;
        }
        return new String[]{replacerName, comment};
    }

    public static List<String[]> parseReplacersSourceString(final String replacersSourceString) {
        final List<String[]> data = new ArrayList<>();
        if (!replacersSourceString.equals("-")) {
            for (final String replacerData : Arrays.asList(replacersSourceString.split("; "))) {
                data.add(parseReplacerData(replacerData));
            }
        }
        return data;
    }

}


















