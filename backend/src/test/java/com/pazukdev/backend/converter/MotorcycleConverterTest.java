package com.pazukdev.backend.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.dto.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.Motorcycle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class MotorcycleConverterTest {

    private final MockData mockData = new MockData();
    private final MotorcycleConverter converter = new MotorcycleConverter();

    @Test
    public void convertToDto() {
        final Motorcycle motorcycle = mockData.motorcycle();
        final MotorcycleDto dto= converter.convertToDto(motorcycle);

        assertEquals(motorcycle.getName(), dto.getName());
        assertEquals(motorcycle.getManufacturer(), dto.getManufacturer());
        assertEquals(motorcycle.getWeightG(), dto.getWeightG());
    }

    @Test
    public void convertToDbo() {
        final MotorcycleDto dto = mockData.motorcycleDto();
        final Motorcycle motorcycle = converter.convertToEntity(dto);

        assertEquals(dto.getName(), motorcycle.getName());
        assertEquals(dto.getManufacturer(), motorcycle.getManufacturer());
        assertEquals(dto.getWeightG(), motorcycle.getWeightG());
    }

}
