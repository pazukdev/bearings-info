package com.pazukdev.backend.dto.factory;

import com.pazukdev.backend.dto.NestedItemDto;
import com.pazukdev.backend.entity.ChildItem;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.Replacer;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.service.UserService;
import com.pazukdev.backend.util.CategoryUtil;
import com.pazukdev.backend.util.ChildItemUtil;
import com.pazukdev.backend.util.SpecificStringUtil;
import com.pazukdev.backend.util.UserUtil;

import static com.pazukdev.backend.util.ItemUtil.*;

/**
 * @author Siarhei Sviarkaltsau
 */
public class NestedItemDtoFactory {

    public static NestedItemDto createUser(final UserEntity user) {
        final String role = SpecificStringUtil.capitalize(user.getRole().name());

        final NestedItemDto userData = new NestedItemDto();
        userData.setItemId(user.getId());
        userData.setItemName(user.getName());
        userData.setButtonText(user.getName());
        userData.setRating(user.getRating());
        userData.setItemCategory(role);
        userData.setComment(role);
        userData.setSecondComment(user.getRating().toString());
        userData.setStatus(user.getStatus());
        return userData;
    }

    public static NestedItemDto createMotorcycle(final Item motorcycle, final UserService userService) {
        final String description = motorcycle.getDescription();
        final String production = getValueFromDescription(description, "Production");
        final String manufacturer = getValueFromDescription(description, "Manufacturer");

        final NestedItemDto motorcycleDto = createBasicNestedItemDto(motorcycle, userService);
        motorcycleDto.setComment(production);
        motorcycleDto.setSecondComment(manufacturer);
        motorcycleDto.setItemCategory(manufacturer);
        motorcycleDto.setDeletable(false);
        return motorcycleDto;
    }

    public static NestedItemDto createChildItem(final ChildItem childItem,
                                                final UserService userService,
                                                final boolean addLocation) {
        final Item item = childItem.getItem();

        final NestedItemDto childItemDto = createBasicNestedItemDto(item, userService);
        childItemDto.setId(childItem.getId());
        childItemDto.setName(childItem.getName());
        if (addLocation) {
            childItemDto.setComment(childItem.getLocation());
        }
        childItemDto.setSecondComment(childItem.getQuantity());
        childItemDto.setStatus(childItem.getStatus());
        return childItemDto;
    }

    public static NestedItemDto createReplacer(final Replacer replacer, final UserService userService) {
        final Item item = replacer.getItem();

        final NestedItemDto replacerDto = createBasicNestedItemDto(item, userService);
        replacerDto.setId(replacer.getId());
        replacerDto.setName(replacer.getName());
        replacerDto.setComment(replacer.getComment());
        replacerDto.setSecondComment("-");
        return replacerDto;
    }

    public static NestedItemDto createWishListItem(final ChildItem childItem, final UserService userService) {
        final Item item = childItem.getItem();

        final NestedItemDto dto = createBasicNestedItemDto(item, userService);
        dto.setId(childItem.getId());
        dto.setName(ChildItemUtil.createNameForWishListItem(item.getName()));
        dto.setComment(childItem.getLocation());
        dto.setSecondComment(childItem.getQuantity());
        return dto;
    }

    public static NestedItemDto createItemForItemsManagement(final Item item, final UserService userService) {
        final NestedItemDto basicSpecialNestedItemDto = createBasicNestedItemDto(item, userService);
        String leftColumnData = CategoryUtil.getItemsManagementComment(item);
        basicSpecialNestedItemDto.setComment(leftColumnData != null ? leftColumnData : "-");
        return basicSpecialNestedItemDto;
    }

    public static NestedItemDto createBasicSpecialNestedItemDto(final Item item, final UserService userService) {
        final NestedItemDto basicSpecialNestedItemDto = createBasicNestedItemDto(item, userService);
        basicSpecialNestedItemDto.setComment(item.getCategory());
        return basicSpecialNestedItemDto;
    }

    public static NestedItemDto createBasicNestedItemDto(final Item item, final UserService userService) {
        final String name = " - " + item.getName();
        final Long itemId = item.getId();
        final String itemName = item.getName();
        final String buttonText = createButtonText(item);
        final String selectText = createSelectText(item);

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
