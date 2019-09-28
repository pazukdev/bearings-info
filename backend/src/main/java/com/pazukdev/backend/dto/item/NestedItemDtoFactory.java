package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.util.ItemUtil;

/**
 * @author Siarhei Sviarkaltsau
 */
public class NestedItemDtoFactory {

    public static NestedItemDto createBasicNestedItemDto(final Item item) {
        final String name = " - " + item.getName();
        final Long itemId = item.getId();
        final String itemName = item.getName();
        final String buttonText = ItemUtil.createButtonText(item);
        final String selectText = ItemUtil.createSelectText(item);

        final NestedItemDto nestedItemDto = new NestedItemDto();
        nestedItemDto.setName(name);
        nestedItemDto.setItemId(itemId);
        nestedItemDto.setItemName(itemName);
        nestedItemDto.setItemCategory(item.getCategory());
        nestedItemDto.setButtonText(buttonText);
        nestedItemDto.setSelectText(selectText);
        return nestedItemDto;
    }

}
