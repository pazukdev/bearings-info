package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.product.Motorcycle;
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
        final Motorcycle entity = mockData.motorcycle();
        final MotorcycleDto dto= converter.convertToDto(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getProductionStartYear(), dto.getProductionStartYear());
        assertEquals(entity.getProductionStopYear(), dto.getProductionStopYear());
        assertEquals(entity.getManufacturer().getId(), dto.getManufacturerId());
        assertEquals(entity.getWeightG(), dto.getWeightG());
    }

    @Test
    public void convertToEntity() {
        final MotorcycleDto dto = mockData.motorcycleDto();
        final Motorcycle entity = converter.convertToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getProductionStartYear(), entity.getProductionStartYear());
        assertEquals(dto.getProductionStopYear(), entity.getProductionStopYear());
        assertEquals(dto.getManufacturerId(), entity.getManufacturer().getId());
        assertEquals(dto.getWeightG(), entity.getWeightG());
    }

}
