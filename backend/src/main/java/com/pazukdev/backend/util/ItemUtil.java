package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.item.ItemQuantity;
import com.pazukdev.backend.dto.item.ReplacerData;
import com.pazukdev.backend.dto.item.TransitiveItemDescriptionMap;
import com.pazukdev.backend.entity.item.ChildItem;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.TransitiveItem;
import com.pazukdev.backend.service.TransitiveItemService;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ItemUtil {

    public static void sort(final List<TransitiveItem> items) {
        items.sort(Comparator.comparing(TransitiveItem::getCategory));
    }

    public static Set<String> findCategories(final List<TransitiveItem> items) {
        final Set<String> categories = new HashSet<>();
        for (final TransitiveItem item : items) {
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

    public static Set<String> getChildItemsCategories(final List<ChildItem> childItems) {
        final Set<String> categories = new HashSet<>();
        for (final ChildItem childItem : childItems) {
            if (childItem.getItem().getCategory() == null) {
                childItem.getItem().setCategory("-");
            }
            categories.add(childItem.getItem().getCategory());
        }
        return categories;
    }

    public static List<List<TransitiveItem>> categorize(final List<TransitiveItem> items) {
        final List<List<TransitiveItem>> categorizedItems = new ArrayList<>();
        for (final String category : findCategories(items)) {
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

    public static List<List<ChildItem>> categorizeChildItems(final List<ChildItem> childItems) {
        final List<List<ChildItem>> categorizedItems = new ArrayList<>();
        for (final String category : getChildItemsCategories(childItems)) {
            categorizedItems.add(childItems.stream()
                    .filter(item -> item.getItem().getCategory().equals(category)).collect(Collectors.toList()));

        }
        return categorizedItems;
    }

    public static String getValueFromDescription(final TransitiveItem item, final String key) {
        return getValueFromDescription(item.getDescription(), key);
    }

    public static String getValueFromDescription(final String description, final String key) {
        return toMap(description).get(key);
    }

    public static String toDescription(final Map<String, String> map) {
        String description = "";
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("Name")) {
                continue;
            }
            description += entry.getKey() + ":" + entry.getValue() + ";;";
        }
        return description;
    }

    public static String toDescription(final TransitiveItemDescriptionMap descriptionMap) {
        final String description
                = toDescription(descriptionMap.getCharacteristics())
                + toDescription(descriptionMap.getSelectableCharacteristics())
                + toDescription(descriptionMap.getItems());
        return SpecificStringUtil.replaceBlankWithDash(description);
    }

    public static Map<String, String> toMap(final String description) {
        final Map<String, String> map = new HashMap<>();
        if (description == null || !description.contains(":")) {
            return map;
        }
        final List<String> descriptionList = Arrays.asList(description.split(";;"));
        for (final String element : descriptionList) {
            map.put(element.split(":")[0], element.split(":")[1]);
        }
        return map;
    }

    public static TransitiveItemDescriptionMap createDescriptionMap(final TransitiveItem item,
                                                                    final TransitiveItemService service) {
        final Map<String, String> unsortedMap = toMap(item.getDescription());
        final TransitiveItemDescriptionMap itemDescriptionMap = new TransitiveItemDescriptionMap();
        itemDescriptionMap.setParent(item);
        for (final Map.Entry<String, String> entry : unsortedMap.entrySet()) {
            final String parameter = StringUtils.trim(entry.getKey());
            final String value = StringUtils.trim(entry.getValue());
            if (isInfoItem(parameter, service)) {
                itemDescriptionMap.getSelectableCharacteristics().put(parameter, value);
            } else if (isLinkToItem(parameter, service)) {
                itemDescriptionMap.getItems().put(parameter, value);
            } else {
                itemDescriptionMap.getCharacteristics().put(parameter, value);
            }
        }
        return itemDescriptionMap;
    }

    public static boolean isLinkToItem(final String parameter, final TransitiveItemService service) {
        return !isInfoItem(parameter, service) && service.find(parameter).size() > 0;
    }

    public static boolean isInfoItem(final String parameter, final TransitiveItemService service) {
        //return findCategories(service.findAll()).contains(parameter + " (i)");
        return service.find(parameter + " (i)").size() > 0;
    }

    public static String getInfoCategory(final String parameter) {
        return parameter + " (i)";
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

    public static String createButtonText(final Item nestedItem) {
        if (isAddManufacturerName(nestedItem)) {
            return getValueFromDescription(nestedItem.getDescription(), "Manufacturer")
                    + " " + nestedItem.getName();
        } else {
            return nestedItem.getName();
        }
    }

    public static String createSelectText(final Item nestedItem) {
        final String manufacturer = getValueFromDescription(nestedItem.getDescription(), "Manufacturer");
        String selectText = nestedItem.getName();
        if (manufacturer != null) {
            selectText = manufacturer + " " + nestedItem.getName();
        }
        if (nestedItem.getCategory().equals("Seal")) {
            final String size = ItemUtil.getValueFromDescription(nestedItem.getDescription(), "Size, mm");
            selectText = size + " " + manufacturer + " " + nestedItem.getName();
        }
        return selectText;
    }

    public static boolean isAddManufacturerName(final Item nestedItem) {
        final String category = nestedItem.getCategory();
        return category.equals("Seal") || category.equals("Spark plug");
    }

    public static boolean itemIsAbleToContainParts(final Item item) {
        return !(item.getCategory().equals("Seal")
                || item.getCategory().equals("Spark plug")
                || item.getCategory().equals("Material")
                || item.getCategory().equals("GOST")
                || item.getCategory().equals("Screw")
                || item.getCategory().equals("Lock ring")
                || item.getCategory().equals("Universal joint cross")
                || item.getCategory().equals("Oil"));
    }

}


















