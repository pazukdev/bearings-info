package com.pazukdev.backend.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.dto.bearing.BearingDto;
import com.pazukdev.backend.entity.Bearing;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class BearingConverterTest {

    private final MockData mockData = new MockData();
    private final BearingConverter converter = new BearingConverter();

    @Test
    public void convertToDto() {
        final Bearing bearing = mockData.bearing();
        final BearingDto dto = converter.convertToDto(bearing);

        assertEquals(bearing.getName(), dto.getName());
        assertEquals(bearing.getType(), dto.getType());
        assertEquals(bearing.getRollingElement(), bearing.getRollingElement());
        assertEquals(bearing.getRollingElementsQuantity(), bearing.getRollingElementsQuantity());
    }

    @Test
    public void convertToDbo() {
        final BearingDto dto = mockData.bearingDto();
        final Bearing bearing = converter.convertToDbo(dto);

        assertEquals(dto.getName(), bearing.getName());
        assertEquals(dto.getType(), bearing.getType());
        assertEquals(dto.getRollingElement(), bearing.getRollingElement());
        assertEquals(dto.getRollingElementsQuantity(), bearing.getRollingElementsQuantity());
    }

    @Test
    public void convertToDtoList() {
        final List<Bearing> bearings = new ArrayList<>();
        bearings.add(mockData.bearing());
        bearings.add(mockData.bearing());

        final List<BearingDto> dtos = converter.convertToDtoList(bearings);

        for (int i = 0; i < bearings.size(); i++) {
            assertEquals(dtos.get(i).getName(), bearings.get(i).getName());
            assertEquals(dtos.get(i).getType(), bearings.get(i).getType());
            assertEquals(dtos.get(i).getRollingElement(), bearings.get(i).getRollingElement());
            assertEquals(dtos.get(i).getRollingElementsQuantity(), bearings.get(i).getRollingElementsQuantity());
        }
    }

}











