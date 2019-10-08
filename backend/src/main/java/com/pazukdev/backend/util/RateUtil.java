package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.item.ItemView;
import com.pazukdev.backend.dto.item.Rate;
import com.pazukdev.backend.entity.LikeList;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.service.ItemService;

public class RateUtil {

    public static void processRateAction(final ItemView itemView,
                                         final UserEntity currentUser,
                                         final ItemService itemService) {
        final Rate rate = itemView.getRate();
        final Long itemId = rate.getItemId();
        final Item itemToRate = itemService.getOne(rate.getItemId());
        final String action = rate.getAction();
        final LikeList likeList = currentUser.getLikeList();

        if (UserUtil.collectRatedItemIds(currentUser).contains(itemId)) {
            if (action.equals("cancel")) {
                if (likeList.getLikedItems().contains(itemToRate)) {
                    likeList.getLikedItems().remove(itemToRate);
                    itemToRate.setRating(itemToRate.getRating() - 1);
                } else {
                    likeList.getDislikedItems().remove(itemToRate);
                    itemToRate.setRating(itemToRate.getRating() + 1);
                }
                itemView.getRatedItems().remove(itemId);
            }
        } else {
            if (action.equals("up")) {
                itemToRate.setRating(itemToRate.getRating() + 1);
                itemService.update(itemToRate);
                currentUser.getLikeList().getLikedItems().add(itemToRate);
            } else if (action.equals("down")) {
                itemToRate.setRating(itemToRate.getRating() - 1);
                itemService.update(itemToRate);
                currentUser.getLikeList().getDislikedItems().add(itemToRate);
            }
            itemView.getRatedItems().add(itemId);
        }

        itemService.getUserService().update(currentUser);
    }

}
