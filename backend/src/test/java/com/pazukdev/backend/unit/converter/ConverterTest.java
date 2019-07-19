package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.entity.User;
import com.pazukdev.backend.entity.product.bearing.Bearing;
import org.junit.Test;

import static com.pazukdev.backend.unit.converter.util.ConverterTestUtil.validateBearingConversion;
import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ConverterTest {

    private final MockData mockData = new MockData();

    @Test
    public void bearingToBearingDto() {
        final Bearing entity = mockData.bearing();
        final BearingDto dto = mockData.getTestContext().getBearingConverter().convertToDto(entity);

        validateBearingConversion(entity, dto);
    }

    @Test
    public void bearingDtoToBearing() {
        final BearingDto dto = mockData.bearingDto();
        final Bearing entity = mockData.getTestContext().getBearingConverter().convertToEntity(dto);

        validateBearingConversion(entity, dto);
    }

    @Test
    public void userDtoToUser() {
        final UserDto dto = mockData.userDto();
        final User user = mockData.getTestContext().getUserConverter().convertToEntity(dto);

        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getLogin(), user.getLogin());
        assertEquals(dto.getPassword(), user.getPassword());
        assertEquals(dto.getRole(), user.getRole().name());
    }

    @Test
    public void userToUserDto() {
        final User user = mockData.user();
        final UserDto dto = mockData.getTestContext().getUserConverter().convertToDto(user);

        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getLogin(), dto.getLogin());
        assertEquals(user.getPassword(), dto.getPassword());
        assertEquals(user.getRole().name(), dto.getRole());
    }


}











