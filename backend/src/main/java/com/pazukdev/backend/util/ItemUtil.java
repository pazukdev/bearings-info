package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.item.ItemDescriptionMap;
import com.pazukdev.backend.dto.item.ItemQuantity;
import com.pazukdev.backend.dto.item.ReplacerData;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.service.ItemService;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
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

    public static String getValueFromDescription(final ItemEntity item, final String key) {
        return getValueFromDescription(item.getDescription(), key);
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

    public static ItemDescriptionMap createDescriptionMap(final ItemEntity item, final ItemService service) {
        final Map<String, String> unsortedMap = toMap(item.getDescription());
        final ItemDescriptionMap itemDescriptionMap = new ItemDescriptionMap();
        itemDescriptionMap.setParent(item);
        for (final Map.Entry<String, String> entry : unsortedMap.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            if (isLinkToItem(entry, service)) {
                itemDescriptionMap.getItems().put(key, value);
            } else {
                itemDescriptionMap.getCharacteristics().put(key, value);
            }
        }
        return itemDescriptionMap;
    }

    public static boolean isLinkToItem(final Map.Entry<String, String> entry, final ItemService service) {
        final String category = StringUtils.trim(entry.getKey());
        return service.find(category).size() > 0;
    }

    public static ReplacerData parseReplacerData(final String replacerDataSourceString) {
        final ReplacerData replacerData = new ReplacerData();
        String replacerName;
        String comment = "-";
        if (SpecificStringUtil.containsParentheses(replacerDataSourceString)) {
            replacerName = SpecificStringUtil.getStringBeforeParentheses(replacerDataSourceString);
            comment = SpecificStringUtil.getStringBetweenParentheses(replacerDataSourceString);
        } else {
            replacerName = replacerDataSourceString;
        }
        replacerData.setName(replacerName);
        replacerData.setComment(comment);
        return replacerData;
    }

    public static List<ReplacerData> parseReplacersSourceString(final String replacersSourceString) {
        final List<ReplacerData> data = new ArrayList<>();
        if (!replacersSourceString.equals("-")) {
            for (final String replacerData : Arrays.asList(replacersSourceString.split("; "))) {
                data.add(parseReplacerData(replacerData));
            }
        }
        return data;
    }

}


















