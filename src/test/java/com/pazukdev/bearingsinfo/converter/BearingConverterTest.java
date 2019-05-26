package com.pazukdev.bearingsinfo.converter;

import com.pazukdev.bearingsinfo.MockData;
import com.pazukdev.bearingsinfo.entity.Bearing;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void convertToDtoList() {
        final List<Bearing> bearings = new ArrayList<>();
        bearings.add(MockData.bearing());
        bearings.add(MockData.bearing());

        final List<BearingDto> dtos = converter.convertToDtoList(bearings);

        for (int i = 0; i < bearings.size(); i++) {
            assertEquals(dtos.get(i).getName(), bearings.get(i).getName());
            assertEquals(dtos.get(i).getType(), bearings.get(i).getType());
            assertEquals(dtos.get(i).getRollingElement(), bearings.get(i).getRollingElement());
            assertEquals(dtos.get(i).getRollingElementsQuantity(), bearings.get(i).getRollingElementsQuantity());
        }
    }

}











