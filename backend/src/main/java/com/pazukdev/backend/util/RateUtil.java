package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.item.ItemView;
import com.pazukdev.backend.dto.item.RateReplacer;
import com.pazukdev.backend.entity.LikeList;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.service.ItemService;
import lombok.Getter;

public class RateUtil {

    @Getter
    public enum RateAction {

        UP("up"),
        DOWN("down"),
        CANCEL("cancel");

        private final String value;

        RateAction(final String value) {
            this.value = value;
        }
    }

    @Getter
    public enum ValueIncrease {

        UP("up"),
        DOWN("down"),
        CANCEL("cancel");

        private final String value;

        ValueIncrease(final String value) {
            this.value = value;
        }
    }

    public static void processRateAction(final ItemView itemView,
                                         final UserEntity currentUser,
                                         final ItemService itemService) {
        final RateReplacer rate = itemView.getRate();
        final Long itemId = rate.getItemId();
        final Item itemToRate = itemService.getOne(rate.getItemId());
        final RateAction rateAction = RateAction.valueOf(rate.getAction());
        final LikeList likeList = currentUser.getLikeList();

        if (UserUtil.collectRatedItemIds(currentUser).contains(itemId)) {
            if (rateAction == RateAction.CANCEL) {
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
            if (rateAction == RateAction.UP) {
                itemToRate.setRating(itemToRate.getRating() + 1);
                itemService.update(itemToRate);
                currentUser.getLikeList().getLikedItems().add(itemToRate);
            } else if (rateAction == RateAction.DOWN) {
                itemToRate.setRating(itemToRate.getRating() - 1);
                itemService.update(itemToRate);
                currentUser.getLikeList().getDislikedItems().add(itemToRate);
            }
            itemView.getRatedItems().add(itemId);
        }

        UserUtil.processRateAction(itemToRate, rateAction, currentUser, itemService);
    }

}
