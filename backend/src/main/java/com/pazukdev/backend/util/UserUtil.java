package com.pazukdev.backend.util;

import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.dto.UserItemReport;
import com.pazukdev.backend.dto.UserItemStringReport;
import com.pazukdev.backend.dto.user.UserDto;
import com.pazukdev.backend.entity.ChildItem;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.UserService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.pazukdev.backend.util.SpecificStringUtil.*;

public class UserUtil {

    public static class UserParam {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ROLE = "role";
        public static final String RATING = "rating";
        public static final String STATUS = "status";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String COUNTRY = "country";
        public static final String CREATED_ITEMS = "created items";
        public static final String WISHLIST_ITEMS = "wishlist items";
        public static final String LIKED_ITEMS = "liked items";
        public static final String DISLIKED_ITEMS = "disliked items";
        public static final String IMG = "img";
    }

    public static boolean isAdmin(final UserEntity user) {
        return user.getRole().equals(Role.ADMIN);
    }

    public static UserDto getCreatorData(final Item item, final UserService service) {
        final UserEntity user = service.findOne(item.getCreatorId());
        if (user != null) {
            final UserDto creatorData = new UserDto();
            creatorData.setId(user.getId());
            creatorData.setName(user.getName());
            creatorData.setStatus(user.getStatus());
            return creatorData;
        }
        return null;
    }

    public static InputStream toCSVInputStream(final ItemService service) throws IOException {
        final String lineSeparator = System.getProperty("line.separator");
        final String comma = ", ";
        final List<String> csvList = new ArrayList<>();
        final String header = UserParam.ID + comma
                + UserParam.NAME + comma
                + UserParam.ROLE + comma
                + UserParam.RATING + comma
                + UserParam.STATUS + comma
                + UserParam.EMAIL + comma
                + UserParam.PASSWORD + comma
                + UserParam.COUNTRY + comma
                + UserParam.CREATED_ITEMS + comma
                + UserParam.WISHLIST_ITEMS + comma
                + UserParam.LIKED_ITEMS + comma
                + UserParam.DISLIKED_ITEMS + comma
                + UserParam.IMG;
        csvList.add(header + lineSeparator);

        for (final UserEntity user : service.getUserService().findAll()) {
            String wishlistItems = "";
            for (final ChildItem childItem : user.getWishList().getItems()) {
                final Long id = childItem.getItem().getId();
                final String comment = isEmpty(childItem.getLocation()) ? "" : childItem.getLocation() + " - ";
                wishlistItems += id + " " + "(" + comment + childItem.getQuantity() + ")" + "; ";
            }
            wishlistItems = removeLastChar(wishlistItems.trim());

            final UserItemReport<String> userItemReport = UserItemStringReport.create(user, service.findAll());

            final String line = user.getId() + comma
                    + user.getName() + comma
                    + user.getRole() + comma
                    + user.getRating() + comma
                    + user.getStatus() + comma
                    + replaceEmptyWithEmpty(user.getEmail()) + comma
                    + user.getPassword() + comma
                    + replaceEmptyWithEmpty(user.getCountry()) + comma
                    + replaceEmptyWithEmpty(setToString(userItemReport.getCreatedItems())) + comma
                    + replaceEmptyWithEmpty(wishlistItems) + comma
                    + replaceEmptyWithEmpty(setToString(userItemReport.getLikedItems())) + comma
                    + replaceEmptyWithEmpty(setToString(userItemReport.getDislikedItems())) + comma
                    + replaceEmptyWithEmpty(user.getImg());
            csvList.add(line + lineSeparator);
        }
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            for (final String line : csvList) {
                baos.write(line.getBytes());
            }
            try (final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
                return bais;
            }
        }

    }

}
