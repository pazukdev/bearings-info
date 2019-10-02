package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.item.ChildItem;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.Replacer;
import com.pazukdev.backend.entity.item.UserAction;

import java.time.LocalDateTime;

public class UserActionUtil {

    public static UserAction create(final UserEntity user,
                                    final String actionType,
                                    final Item item,
                                    final ChildItem part) {
        final String itemType = "part";

        final UserAction userAction = create(user, actionType, itemType, item);
        userAction.setParentItemId(part.getId().toString());
        userAction.setItemType(itemType);

        return userAction;
    }

    public static UserAction create(final UserEntity user,
                                    final String actionType,
                                    final Item item,
                                    final Replacer replacer) {
        final String itemType = "replacer";

        final UserAction userAction = create(user, actionType, itemType, item);
        userAction.setParentItemId(replacer.getId().toString());
        userAction.setItemType(itemType);

        return userAction;
    }

    public static UserAction create(final UserEntity user,
                                    final String actionType,
                                    final String itemType,
                                    final Item item) {
        final String name = createName(actionType, itemType, item.getName());

        final UserAction userAction = new UserAction();

        userAction.setName(name);
        userAction.setActionType(actionType);
        userAction.setActionDate(LocalDateTime.now().toString());

        userAction.setUserId(user.getId().toString());

        userAction.setItemId(item.getId().toString());
        userAction.setItemCategory(item.getCategory());
        userAction.setItemType("item");

        return userAction;
    }

    private static String createName(final String actionType, final String itemType, final String itemName) {
        return actionType + " " + itemType + " " + itemName;
    }

}
