package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.LikeList;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.UserAction;
import com.pazukdev.backend.service.ItemService;
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

    public static void updateRatingOnItemCreation(final String itemCategory,
                                                  final String userName,
                                                  final UserService userService) {
        final UserEntity currentUser = userService.findByName(userName);
        final Integer increase = itemCategory.equals("Motorcycle") ? 20 : 10;
        currentUser.setRating(currentUser.getRating() + increase);
    }

    public static void updateRatingOnReplacerCreation(final UserEntity currentUser, final UserService userService) {
        //final UserEntity currentUser = userService.findByName(userName);
        final Integer increase = 6;
        currentUser.setRating(currentUser.getRating() + increase);
    }

    public static void updateRatingOnPartCreation(final UserEntity currentUser, final UserService userService) {
        //final UserEntity currentUser = userService.findByName(userName);
        final Integer increase = 4;
        currentUser.setRating(currentUser.getRating() + increase);
    }

    public static void updateRatingOnUpdate(final UserEntity currentUser, final UserService userService) {
        //final UserEntity currentUser = userService.findByName(userName);
        final Integer increase = 4;
        currentUser.setRating(currentUser.getRating() + increase);
    }

    public static void updateRatingOnItemRating(final String userName, final UserService userService) {
        final UserEntity currentUser = userService.findByName(userName);
        final Integer increase = 1;
        currentUser.setRating(currentUser.getRating() + increase);
    }

    public static void processRateAction(final Item itemToRate,
                                         final RateUtil.RateAction rateAction,
                                         final UserEntity currentUser,
                                         final ItemService itemService) {
        if (rateAction != RateUtil.RateAction.CANCEL) {
            updateRatingOnItemRating(currentUser.getName(), itemService.getUserService());
        }
        final UserAction action = UserActionUtil.createRateAction(itemToRate, rateAction, currentUser);
        itemService.getUserActionRepository().save(action);

    }

}
