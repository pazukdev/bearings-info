package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import org.junit.Test;

import static com.pazukdev.backend.unit.converter.util.ConverterTestUtil.validateBearingConversion;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ConverterTest {

    private final MockData mockData = new MockData();

    @Test
    public void bearingToBearingDto() {
        final BearingEntity entity = mockData.bearing();
        final BearingDto dto = mockData.getTestContext().getBearingConverter().convertToDto(entity);

        validateBearingConversion(entity, dto);
    }

    @Test
    public void bearingDtoToBearing() {
        final BearingDto dto = mockData.bearingDto();
        final BearingEntity entity = mockData.getTestContext().getBearingConverter().convertToEntity(dto);

        validateBearingConversion(entity, dto);
    }

//    @Test
//    public void userDtoToUser() {
//        final CredentialsDto dto = mockData.userDto();
//        final User user = mockData.getTestContext().getUserConverter().convertToEntity(dto);
//
//        assertEquals(dto.getName(), user.getName());
//        assertEquals(dto.getName(), user.getName());
//        assertEquals(dto.getPassword(), user.getPassword());
//        assertEquals(dto.getRole(), user.getRole().name());
//    }
//
//    @Test
//    public void userToUserDto() {
//        final User user = mockData.user();
//        final CredentialsDto dto = mockData.getTestContext().getUserConverter().convertToDto(user);
//
//        assertEquals(user.getName(), dto.getName());
//        assertEquals(user.getName(), dto.getName());
//        assertEquals(user.getPassword(), dto.getPassword());
//        assertEquals(user.getRole().name(), dto.getRole());
//    }


}











