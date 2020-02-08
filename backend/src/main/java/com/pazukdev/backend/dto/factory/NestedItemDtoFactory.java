package com.pazukdev.backend.dto.factory;

import com.pazukdev.backend.dto.NestedItemDto;
import com.pazukdev.backend.entity.ChildItem;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.Replacer;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.service.UserService;
import com.pazukdev.backend.util.ChildItemUtil;

import java.util.Map;
import java.util.Set;

import static com.pazukdev.backend.util.CategoryUtil.*;
import static com.pazukdev.backend.util.ItemUtil.*;
import static com.pazukdev.backend.util.SpecificStringUtil.capitalize;
import static com.pazukdev.backend.util.SpecificStringUtil.isEmpty;
import static com.pazukdev.backend.util.UserUtil.getCreatorName;

/**
 * @author Siarhei Sviarkaltsau
 */
public class NestedItemDtoFactory {

    public static NestedItemDto createUser(final UserEntity user) {
        final String role = capitalize(user.getRole().name());

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

    public static NestedItemDto createVehicle(final Item vehicle, final UserService userService) {
        final String description = vehicle.getDescription();
        final Map<String, String> map = toMap(description);
        final String production = map.get(Parameter.PRODUCTION);
        final String manufacturer = map.get(Category.MANUFACTURER);
        final String vehicleClass = map.get(Parameter.CLASS);

        final NestedItemDto vehicleDto = createBasicNestedItemDto(vehicle, userService);
        vehicleDto.setComment(production);
        vehicleDto.setSecondComment(manufacturer);
        vehicleDto.setItemCategory(manufacturer);
        vehicleDto.setDeletable(false);
        vehicleDto.setVehicleIcon(vehicle.getImg());
        vehicleDto.setVehicleClass(vehicleClass);

        return vehicleDto;
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

    public static NestedItemDto createItemForItemsManagement(final Item item,
                                                             final UserService userService,
                                                             final Set<String> comments) {
        final NestedItemDto basicSpecialNestedItemDto = createBasicNestedItemDto(item, userService);
        String leftColumnData = getItemsManagementComment(item, comments);
        basicSpecialNestedItemDto.setComment(!isEmpty(leftColumnData) ? leftColumnData : "-");
        return basicSpecialNestedItemDto;
    }

    public static NestedItemDto createBasicNestedItemDto(final Item item, final UserService userService) {
        final Map<String, String> descriptionMap = toMap(item.getDescription());
        final String manufacturer = descriptionMap.get(Category.MANUFACTURER);

        final String name = " - " + item.getName();
        final Long itemId = item.getId();
        final String itemName = item.getName();
        final String buttonText = createButtonText(item, manufacturer);
        final String selectText = createSelectText(item, manufacturer, descriptionMap);

        final NestedItemDto nestedItemDto = new NestedItemDto();
        nestedItemDto.setName(name);
        nestedItemDto.setItemId(itemId);
        nestedItemDto.setItemName(itemName);
        nestedItemDto.setItemCategory(item.getCategory());
        nestedItemDto.setRating(item.getRating());
        nestedItemDto.setButtonText(buttonText);
        nestedItemDto.setSelectText(selectText);
        nestedItemDto.setStatus(item.getStatus());
        nestedItemDto.setCreatorName(getCreatorName(item, userService));
        return nestedItemDto;
    }

}
