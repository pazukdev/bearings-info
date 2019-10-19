package com.pazukdev.backend;

import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.entity.TransitiveItem;
import com.pazukdev.backend.entity.UserEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Getter
public class MockData {

    private final TestContext testContext = TestContext.create();

    public UserDto userDto() {
        final UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("login");
        dto.setPassword("password");
        return dto;
    }

    public UserEntity user() {
        final UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("login");
        user.setPassword("password");
        return user;
    }

    public List<TransitiveItem> itemsList() {
        final TransitiveItem item1 = new TransitiveItem();
        item1.setCategory("first category");
        final TransitiveItem item2 = new TransitiveItem();
        item2.setCategory("second category");
        final TransitiveItem item3 = new TransitiveItem();
        item3.setCategory("second category");
        final TransitiveItem item4 = new TransitiveItem();
        item4.setCategory("second category");
        final TransitiveItem item5 = new TransitiveItem();
        item5.setCategory("second category");
        final TransitiveItem item6 = new TransitiveItem();
        item6.setCategory("third category");
        return new ArrayList<>(Arrays.asList(item1, item2, item3, item4, item5, item6));
    }

}


















