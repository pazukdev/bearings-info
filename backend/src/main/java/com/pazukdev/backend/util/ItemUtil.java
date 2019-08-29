package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.item.ItemEntity;

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

    public static List<List<ItemEntity>> categorize(final List<ItemEntity> items) {
        final List<List<ItemEntity>> categorizedItems = new ArrayList<>();
        for (final String category : getCategories(items)) {
            categorizedItems.add(items.stream()
                    .filter(item -> item.getCategory().equals(category)).collect(Collectors.toList()));

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

}


















