package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.dto.user.UserDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.WishList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ConverterTest {

    private final MockData mockData = new MockData();

    @Test
    public void wishListEntityToWishListDto() {
        final WishList entity = new WishList();
        final WishListDto dto = mockData.getTestContext().getWishListConverter().convertToDto(entity);

        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    public void wishListDtoToWishListEntity() {
        final WishListDto dto = new WishListDto();
        final WishList entity = mockData.getTestContext().getWishListConverter().convertToEntity(dto);

        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    public void userDtoToUser() {
        final UserDto dto = mockData.userDto();
        final UserEntity user = mockData.getTestContext().getUserConverter().convertToEntity(dto);

        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getPassword(), user.getPassword());
    }

    @Test
    public void userToUserDto() {
        final UserEntity user = mockData.user();
        final UserDto dto = mockData.getTestContext().getUserConverter().convertToDto(user);

        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getPassword(), dto.getPassword());
    }

}











