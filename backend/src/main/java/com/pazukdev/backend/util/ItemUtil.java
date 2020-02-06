package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.ItemQuantity;
import com.pazukdev.backend.dto.ReplacerData;
import com.pazukdev.backend.dto.TransitiveItemDescriptionMap;
import com.pazukdev.backend.dto.table.HeaderTable;
import com.pazukdev.backend.dto.table.HeaderTableRow;
import com.pazukdev.backend.dto.view.ItemView;
import com.pazukdev.backend.entity.*;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.TransitiveItemService;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.pazukdev.backend.util.CategoryUtil.isAddManufacturerName;
import static com.pazukdev.backend.util.CategoryUtil.isInfo;
import static com.pazukdev.backend.util.UserActionUtil.ActionType.*;
import static com.pazukdev.backend.util.UserActionUtil.processPartAction;
import static com.pazukdev.backend.util.UserActionUtil.processReplacerAction;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ItemUtil {

    public static final String MULTI_PARAM_SEPARATOR = " #";

    @Getter
    public enum SpecialItemId {

        ITEMS_MANAGEMENT_VIEW(-1L),
        MOTORCYCLE_CATALOGUE_VIEW(-2L),
        WISH_LIST_VIEW(-3L),
        USER_LIST_VIEW(-4L);

        private final Long itemId;

        SpecialItemId(final Long itemId) {
            this.itemId = itemId;
        }
    }

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
        return getValueFromDescriptionMap(toMap(description), key);
    }

    public static String getValueFromDescriptionMap(final Map<String, String> map, final String key) {
        String value = map.get(key);
        if (value == null) {
            for (int i = 0; i < 10; i++) {
                value = map.get(key + " #" + i);
                if (value != null) {
                    return value;
                }
            }
        }
        return value;
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
                = toDescription(descriptionMap.getParameters())
                + toDescription(descriptionMap.getSelectableParameters())
                + toDescription(descriptionMap.getItems());
        return SpecificStringUtil.replaceEmptyWithDash(description);
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
                                                                    final TransitiveItemService service,
                                                                    final Set<String> infoCategories) {
        final Map<String, String> unsortedMap = toMap(item.getDescription());
        final TransitiveItemDescriptionMap itemDescriptionMap = new TransitiveItemDescriptionMap();
        itemDescriptionMap.setParent(item);
        for (final Map.Entry<String, String> entry : unsortedMap.entrySet()) {
            String parameter = StringUtils.trim(entry.getKey());
            final String value = StringUtils.trim(entry.getValue());
            if (isInfo(parameter, infoCategories)) {
                itemDescriptionMap.getParameters().put(parameter, value);
            } else if (service.isPart(parameter, infoCategories)) {
                itemDescriptionMap.getItems().put(parameter, value);
            } else {
                itemDescriptionMap.getParameters().put(parameter, value);
            }
        }
        return itemDescriptionMap;
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

    public static Item getUssrSealBySize(final String searchingSize, final ItemService itemService) {
        final List<Item> ussrSeals = filter(itemService.find("Seal"), "Manufacturer", "USSR");
        for (Item seal : ussrSeals) {
            final String actualSize = ItemUtil.getValueFromDescription(seal.getDescription(), "Size, mm");
            if (actualSize.equals(searchingSize)) {
                return seal;
            }
        }
        return null;
    }

    public static List<Item> filter(final List<Item> items,
                                    final String parameter,
                                    final String searchingValue) {
        final List<Item> filteredItems = new ArrayList<>();
        for (Item item : items) {
            final String value = ItemUtil.getValueFromDescription(item.getDescription(), parameter);
            if (value != null && value.equals(searchingValue)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public static void updateName(final Item item,
                                  final Map<String, String> newDescriptionMap,
                                  final List<Item> allItems,
                                  final ItemService service) {
        final String oldName = item.getName();
        final String newName = newDescriptionMap.get("Name");
        if (newName != null && !newName.equals(oldName)) {
            item.setName(newName);
            applyToAllItemDescriptions(item.getCategory(), oldName, newName, allItems, service);
        }
        newDescriptionMap.remove("Name");
    }

    public static void updateDescription(final Item item,
                                         final HeaderTable headerTable,
                                         final Map<String, String> newDescriptionMap,
                                         final String newDescription,
                                         final List<Item> allItems,
                                         final ItemService itemService) {
        applyNewDescriptionToCategory(item.getCategory(), headerTable, newDescriptionMap, allItems, itemService);
        item.setDescription(newDescription);
    }

    public static void updateChildItems(final Item item,
                                        final ItemView itemView,
                                        final ItemService itemService,
                                        final UserEntity user) {
        final Set<ChildItem> oldChildItems = new HashSet<>(item.getChildItems());
        final Set<ChildItem> newChildItems
                = new HashSet<>(ChildItemUtil.createChildrenFromItemView(itemView, itemService));
        item.getChildItems().clear();
        item.getChildItems().addAll(newChildItems);

        for (final ChildItem child : newChildItems) {
            if (child.getId() == null) {
                processPartAction(ADD, child, item, user, itemService);
            }
        }

        final List<ChildItem> toSave = new ArrayList<>();
        for (final ChildItem oldChild : oldChildItems) {
            for (final ChildItem newChild : newChildItems) {
                if (newChild.getName().equals(oldChild.getName())) {
                    toSave.add(oldChild);
                    if (!newChild.getLocation().equals(oldChild.getLocation())
                            || !newChild.getQuantity().equals(oldChild.getQuantity())) {
                        processPartAction(UPDATE, oldChild, item, user, itemService);
                    }
                }
            }
        }

        oldChildItems.removeAll(toSave);

        for (final ChildItem orphan : oldChildItems) {
            itemService.getChildItemRepository().deleteById(orphan.getId());
            processPartAction(DELETE, orphan, item, user, itemService);
        }
    }

    public static void updateReplacers(final Item item,
                                       final ItemView itemView,
                                       final ItemService itemService,
                                       final UserEntity user) {
        final Set<Replacer> oldReplacers = new HashSet<>(item.getReplacers());
        final Set<Replacer> newReplacers =
                new HashSet<>(ReplacerUtil.createReplacersFromItemView(itemView, itemService));
        item.getReplacers().clear();
        item.getReplacers().addAll(newReplacers);

        for (final Replacer replacer : newReplacers) {
            if (replacer.getId() == null) {
                processReplacerAction(ADD, replacer, item, user, itemService);
            }
        }

        final List<Replacer> toSave = new ArrayList<>();
        for (final Replacer oldReplacer : oldReplacers) {
            for (final Replacer newReplacer : newReplacers) {
                if (newReplacer.getName().equals(oldReplacer.getName())) {
                    toSave.add(oldReplacer);
                    if (!newReplacer.getComment().equals(oldReplacer.getComment())) {
                        processReplacerAction(UPDATE, oldReplacer, item, user, itemService);
                    }
                }
            }
        }

        oldReplacers.removeAll(toSave);

        for (final Replacer orphan : oldReplacers) {
            itemService.getReplacerRepository().deleteById(orphan.getId());
            processReplacerAction(DELETE, orphan, item, user, itemService);
        }
    }

    private static void applyToAllItemDescriptions(final String updatingItemCategory,
                                                   final String oldValue,
                                                   final String newValue,
                                                   final List<Item> allItems,
                                                   final ItemService service) {
        final Set<String> categories = service.collectCategories(allItems);
        for (final Item item : allItems) {
            final Map<String, String> descriptionMap = ItemUtil.toMap(item.getDescription());
            for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
                final String value = entry.getValue().split(" \\(")[0];
                if (value.equals(oldValue)) {
                    if (categories.contains(entry.getKey())) {
                        if (entry.getKey().equals(updatingItemCategory)) {
                            entry.setValue(entry.getValue().replace(oldValue, newValue));
                        }
                    } else {
                        entry.setValue(entry.getValue().replace(oldValue, newValue));
                    }
                }
            }
            final String newDescription = ItemUtil.toDescription(descriptionMap);
            item.setDescription(newDescription);
            service.update(item);
        }
    }

    private static void applyNewDescriptionToCategory(final String category,
                                                      final HeaderTable headerTable,
                                                      final Map<String, String> newDescriptionMap,
                                                      final List<Item> allItems,
                                                      final ItemService itemService) {
        for (final Item item : itemService.find(category, allItems)) {
            final Map<String, String> oldDescriptionMap = ItemUtil.toMap(item.getDescription());
            for (final HeaderTableRow row : headerTable.getRows()) {
                final String oldParam = row.getName();
                final String newParam = row.getParameter();
                boolean paramChanged = !oldParam.equals(newParam);
                if (paramChanged) {
                    final String value = oldDescriptionMap.get(oldParam);
                    newDescriptionMap.put(newParam, value == null ? "-" : value);
                }
            }
            item.setDescription(ItemUtil.toDescription(newDescriptionMap));
        }
    }

    public static Set<Long> collectIds(final Set<Item> items) {
        final Set<Long> ids = new HashSet<>();
        for (final Item item : items) {
            ids.add(item.getId());
        }
        return ids;
    }

}


















