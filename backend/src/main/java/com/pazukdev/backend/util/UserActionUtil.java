package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.item.ChildItem;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.Replacer;
import com.pazukdev.backend.entity.item.UserAction;

import java.time.LocalDateTime;

public class UserActionUtil {

    public static UserAction createRateAction(final Item itemToRate, final String rateAction, final UserEntity user) {
        final String actionType = "rate " + rateAction;
        final String itemType = "replacer";

        return create(user, actionType, itemType, itemToRate);
    }

    public static UserAction createChildItemAction(final UserEntity user,
                                                   final String actionType,
                                                   final Item item,
                                                   final ChildItem childItem) {
        final String itemType = "child item";
        final Long partId = childItem.getId();

        final UserAction userAction = create(user, actionType, itemType, item);
        userAction.setItemId(partId != null ? partId.toString() : "-");
        userAction.setName(createName(actionType, itemType, childItem.getName()));
        userAction.setParentItemId(item.getId().toString());
        userAction.setItemType(itemType);

        return userAction;
    }

    public static UserAction createReplacerAction(final UserEntity user,
                                                  final String actionType,
                                                  final Item item,
                                                  final Replacer replacer) {
        final String itemType = "replacer";
        final Long replacerId = replacer.getId();

        final UserAction userAction = create(user, actionType, itemType, item);
        userAction.setItemId(replacerId != null ? replacerId.toString() : "-");
        userAction.setName(createName(actionType, itemType, replacer.getName()));
        userAction.setParentItemId(item.getId().toString());
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
        userAction.setItemType(itemType);

        return userAction;
    }

    public static String createName(final String actionType, final String itemType, final String itemName) {
        return actionType + " " + itemType + " " + itemName;
    }

}
