package com.pazukdev.bearingsinfo.converter;

import com.pazukdev.bearingsinfo.MockData;
import com.pazukdev.bearingsinfo.entity.Bearing;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class BearingConverterTest {

    private final BearingConverter converter = new BearingConverter();

    @Test
    public void convertToDto() {
        final Bearing bearing = MockData.bearing();
        final BearingDto dto = converter.convertToDto(bearing);

        assertEquals(bearing.getName(), dto.getName());
        assertEquals(bearing.getType(), dto.getType());
        assertEquals(bearing.getRollingElement(), bearing.getRollingElement());
        assertEquals(bearing.getRollingElementsQuantity(), bearing.getRollingElementsQuantity());
    }

    @Test
    public void convertToDbo() {
        final BearingDto dto = MockData.bearingDto();
        final Bearing bearing = converter.convertToDbo(dto);

        assertEquals(dto.getName(), bearing.getName());
        assertEquals(dto.getType(), bearing.getType());
        assertEquals(dto.getRollingElement(), bearing.getRollingElement());
        assertEquals(dto.getRollingElementsQuantity(), bearing.getRollingElementsQuantity());
    }

}
