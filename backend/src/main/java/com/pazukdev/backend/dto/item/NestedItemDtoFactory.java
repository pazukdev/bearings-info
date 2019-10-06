package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.entity.item.ChildItem;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.service.UserService;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.UserUtil;

/**
 * @author Siarhei Sviarkaltsau
 */
public class NestedItemDtoFactory {

    public static NestedItemDto createBasicSpecialNestedItemDto(final Item item, final UserService userService) {
        final NestedItemDto basicNestedItemDto = createBasicNestedItemDto(item, userService);
        basicNestedItemDto.setLocation(item.getCategory());
        return basicNestedItemDto;
    }

    public static NestedItemDto createPart(final ChildItem part, final UserService userService) {
        final Item partItem = part.getItem();

        final NestedItemDto basicNestedItemDto = createBasicNestedItemDto(partItem, userService);
        basicNestedItemDto.setId(part.getId());
        basicNestedItemDto.setName(part.getName());
        basicNestedItemDto.setQuantity(part.getQuantity());
        basicNestedItemDto.setLocation(part.getLocation());
        basicNestedItemDto.setStatus(part.getStatus());
        return basicNestedItemDto;
    }

    public static NestedItemDto createBasicNestedItemDto(final Item item, final UserService userService) {
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
        nestedItemDto.setStatus(item.getStatus());
        nestedItemDto.setCreatorName(UserUtil.getCreatorName(item, userService));
        return nestedItemDto;


    }

}
