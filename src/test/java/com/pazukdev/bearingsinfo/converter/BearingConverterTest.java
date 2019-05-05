package com.pazukdev.bearingsinfo.converter;

import com.pazukdev.bearingsinfo.MockData;
import com.pazukdev.bearingsinfo.dbo.Bearing;
import com.pazukdev.bearingsinfo.dto.BearingDto;
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
        final BearingDto bearingDto = converter.convertToDto(bearing);

        assertEquals(bearing.getName(), bearingDto.getName());
        assertEquals(bearing.getMajorLocation(), bearingDto.getMajorLocation());
        assertEquals(bearing.getQuantity(), bearing.getQuantity());
    }

    @Test
    public void convertToDbo() {
        final BearingDto bearingDto = MockData.bearingDto();
        final Bearing bearing = converter.convertToDbo(bearingDto);

        assertEquals(bearingDto.getName(), bearing.getName());
        assertEquals(bearingDto.getMajorLocation(), bearing.getMajorLocation());
        assertEquals(bearingDto.getQuantity(), bearing.getQuantity());
    }

}
