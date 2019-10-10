package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.LikeList;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.service.UserService;

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

    public static Set<Long> collectRatedItemIds(final UserEntity user) {
        final LikeList likeList = user.getLikeList();
        final Set<Long> ratedItemIds = new HashSet<>();
        ratedItemIds.addAll(ItemUtil.collectIds(likeList.getLikedItems()));
        ratedItemIds.addAll(ItemUtil.collectIds(likeList.getDislikedItems()));
        return ratedItemIds;
    }

    public static String getCreatorName(final Item item, final UserService userService) {
        return userService.getOne(item.getCreatorId()).getName();
    }

}
