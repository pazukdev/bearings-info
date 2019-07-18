package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.entity.product.bearing.Bearing;
import org.junit.Test;

import static com.pazukdev.backend.unit.converter.util.ConverterTestUtil.validateBearingConversion;

/**
 * @author Siarhei Sviarkaltsau
 */
public class BearingConverterTest {

    private final MockData mockData = new MockData();
    private final BearingConverter converter = new BearingConverter();

    @Test
    public void convertToDto() {
        final Bearing entity = mockData.bearing();
        final BearingDto dto = converter.convertToDto(entity);

        validateBearingConversion(entity, dto);
    }

    @Test
    public void convertToDbo() {
        final BearingDto dto = mockData.bearingDto();
        final Bearing entity = converter.convertToEntity(dto);

        validateBearingConversion(entity, dto);

    }

    @Test
    public void convertToDtoSet() {
        converter.convertToDtoSet(mockData.bearings());
    }

}











