package com.pazukdev.backend.dto.factory;

import com.pazukdev.backend.dto.user.UserDto;
import com.pazukdev.backend.entity.UserEntity;

public class UserDtoFactory {

    public static UserDto createItemViewUserData(final UserEntity user) {
        final UserDto userData = new UserDto();
        userData.setId(user.getId());
        userData.setName(user.getName());
        userData.setRole(user.getRole().name());
        userData.setEmail(user.getEmail());
        userData.setRating(user.getRating());
        userData.setStatus(user.getStatus());
        userData.setWishListId(user.getWishList().getId());
        return userData;
    }

}
