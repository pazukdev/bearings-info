package com.pazukdev.backend;

import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.converter.WishListConverter;
import lombok.Data;
import org.modelmapper.ModelMapper;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class TestContext {

    private final ModelMapper modelMapper = new ModelMapper();
    private final UserConverter userConverter;
    private final WishListConverter wishListConverter;

    public static TestContext create() {
        return new TestContext();
    }

    private TestContext() {
        this.userConverter = new UserConverter(modelMapper);
        this.wishListConverter = new WishListConverter(modelMapper);
    }

}
























