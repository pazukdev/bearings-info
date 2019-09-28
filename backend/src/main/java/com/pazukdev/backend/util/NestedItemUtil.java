package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.item.NestedItemDto;
import com.pazukdev.backend.entity.item.Item;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NestedItemUtil {

    public static List<NestedItemDto> prepareNestedItemDtosToConverting(final Item parentItem,
                                                                        final List<NestedItemDto> dtos) {
//        for (final NestedItemDto dto : dtos) {
//            dto.setName(createName(parentItem.getName(), dto.getName()));
//        }
        correctFieldsValues(dtos);

        final List<NestedItemDto> hasId = new ArrayList<>();
        final List<NestedItemDto> noId = new ArrayList<>();
        for (final NestedItemDto dto : dtos) {
            if (dto.getId() != null) {
                hasId.add(dto);
            } else {
                noId.add(dto);
            }
        }
        List<NestedItemDto> list = removeEqualNestedItems(hasId);
        list.addAll(removeEqualNestedItems(noId));
        return removeEqualNestedItems(list);
    }

    public static Set<NestedItemDto> findEqualNestedItemsInList(final List<NestedItemDto> nestedItems,
                                                                final NestedItemDto checkingNestedItem) {
        final Long checkingNestedItemId = checkingNestedItem.getId();
        final Long checkingNestedItemItemId = checkingNestedItem.getItemId();
        final String checkingNestedItemName = checkingNestedItem.getName();
        //final String checkingNestedItemComment = checkingNestedItem.getComment();

        final Set<NestedItemDto> equalNestedItems = new HashSet<>();
        for (final NestedItemDto nestedItem : nestedItems) {
            final Long nestedItemId = nestedItem.getId();
            if (nestedItemId != null && nestedItemId.equals(checkingNestedItemId)) {
                equalNestedItems.add(nestedItem);
                continue;
            }

            final Long nestedItemItemId = nestedItem.getItemId();
            final String nestedItemName = nestedItem.getName();
            final String nestedItemComment = nestedItem.getComment();
            if (nestedItemItemId.equals(checkingNestedItemItemId)
                    && nestedItemName.equals(checkingNestedItemName)) {
                equalNestedItems.add(nestedItem);
            }
        }
        return equalNestedItems;
    }

    public static List<NestedItemDto> removeEqualNestedItems(final List<NestedItemDto> unfiltered) {
        final List<NestedItemDto> filtered = new ArrayList<>();
        for (final NestedItemDto nestedItem : unfiltered) {
            if (equalNestedItemsAlreadyInList(filtered, nestedItem)) {
                continue;
            }
            if (nestedItem.getId() != null) {
                filtered.add(nestedItem);
                continue;
            }
            NestedItemDto toSave = nestedItem;
            final Set<NestedItemDto> equalOldNestedItems = findEqualNestedItemsInList(unfiltered, nestedItem);
            for (final NestedItemDto dto : equalOldNestedItems) {
                if (dto.getId() != null) {
                    toSave = dto;
                }
            }
            filtered.add(toSave);
        }
        return filtered;
    }

    public static boolean equalNestedItemsAlreadyInList(final List<NestedItemDto> nestedItems,
                                                  final NestedItemDto checkingNestedItem) {
        final Long checkingNestedItemId = checkingNestedItem.getId();
        for (final NestedItemDto nestedItem : nestedItems) {
            final Long nestedItemId = nestedItem.getId();
            if (nestedItemId != null && nestedItemId.equals(checkingNestedItemId)) {
                return true;
            }
            if (nestedItem.getName().equals(checkingNestedItem.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void correctFieldsValues(final List<NestedItemDto> dtos) {
        for (final NestedItemDto dto : dtos) {
            final String comment = dto.getComment();
            final String location = dto.getLocation();
            final String quantity = dto.getQuantity();
            if (StringUtils.isBlank(comment)) {
                dto.setComment("-");
            }
            if (StringUtils.isBlank(location)) {
                dto.setLocation("-");
            }
            if (StringUtils.isBlank(quantity)) {
                dto.setQuantity("0");
            }
        }
    }

    public static String createName(final String parentItemName, final String nestedItemName) {
        return parentItemName + " - " + nestedItemName;
    }

}
