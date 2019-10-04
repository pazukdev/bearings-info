package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.item.Item;

import java.util.HashSet;
import java.util.Set;

public class UserUtil {

    public static Set<Long> collectWishListItemsIds(final UserEntity currentUser) {
        Set<Long> ids = new HashSet<>();
        for (final Item item : currentUser.getWishList().getItems()) {
            ids.add(item.getId());
        }
        return ids;
    }

}
