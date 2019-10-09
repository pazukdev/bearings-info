package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.item.ChildItem;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.Replacer;
import com.pazukdev.backend.service.UserService;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.UserUtil;

/**
 * @author Siarhei Sviarkaltsau
 */
public class NestedItemDtoFactory {

    public static NestedItemDto createUser(final UserEntity currentUser) {
        final NestedItemDto userData = new NestedItemDto();
        userData.setItemId(currentUser.getId());
        userData.setItemName(currentUser.getName());
        userData.setRating(currentUser.getRating());
        userData.setComment(currentUser.getRole() == Role.ADMIN ? "admin" : "user");
        return userData;
    }

    public static NestedItemDto createMotorcycle(final Item motorcycle, final UserService userService) {
        final String production = ItemUtil.getValueFromDescription(motorcycle.getDescription(), "Production");

        final NestedItemDto motorcycleDto = createBasicNestedItemDto(motorcycle, userService);
        motorcycleDto.setLocation(production);
        return motorcycleDto;
    }

    public static NestedItemDto createChildItem(final ChildItem childItem, final UserService userService) {
        final Item item = childItem.getItem();

        final NestedItemDto childItemDto = createBasicNestedItemDto(item, userService);
        childItemDto.setId(childItem.getId());
        childItemDto.setName(childItem.getName());
        childItemDto.setQuantity(childItem.getQuantity());
        childItemDto.setLocation(childItem.getLocation());
        childItemDto.setStatus(childItem.getStatus());
        return childItemDto;
    }

    public static NestedItemDto createReplacer(final Replacer replacer, final UserService userService) {
        final Item item = replacer.getItem();

        final NestedItemDto replacerDto = createBasicNestedItemDto(item, userService);
        replacerDto.setId(replacer.getId());
        replacerDto.setName(replacer.getName());
        replacerDto.setComment(replacer.getComment());
        replacerDto.setQuantity("-");
        replacerDto.setLocation("-");
        return replacerDto;
    }

    public static NestedItemDto createBasicSpecialNestedItemDto(final Item item, final UserService userService) {
        final NestedItemDto basicSpecialNestedItemDto = createBasicNestedItemDto(item, userService);
        basicSpecialNestedItemDto.setLocation(item.getCategory());
        return basicSpecialNestedItemDto;
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
        nestedItemDto.setRating(item.getRating());
        nestedItemDto.setButtonText(buttonText);
        nestedItemDto.setSelectText(selectText);
        nestedItemDto.setStatus(item.getStatus());
        nestedItemDto.setCreatorName(UserUtil.getCreatorName(item, userService));
        return nestedItemDto;


    }

}
