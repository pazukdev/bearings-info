package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.DefaultConverter;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.dto.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.Manufacturer;
import com.pazukdev.backend.entity.Motorcycle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class DefaultConverterTest {

    private final MockData mockData = new MockData();

    @Test
    public void convertManufacturerToDto() {
        final DefaultConverter<Manufacturer, ManufacturerDto> converter = new DefaultConverter<>();

        final Manufacturer entity = mockData.manufacturer();
        final ManufacturerDto dto = converter.convertToDto(entity, ManufacturerDto.class);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getFounded(), dto.getFounded());
        assertEquals(entity.getDefunct(), dto.getDefunct());
    }

    @Test
    public void convertManufacturerDtoToEntity() {
        final DefaultConverter<Manufacturer, ManufacturerDto> converter = new DefaultConverter<>();

        final ManufacturerDto dto = mockData.manufacturerDto();
        final Manufacturer entity = converter.convertToEntity(dto, Manufacturer.class);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getFounded(), entity.getFounded());
        assertEquals(dto.getDefunct(), entity.getDefunct());
    }

    @Test
    public void convertMotorcycleToDto() {
        final DefaultConverter<Motorcycle, MotorcycleDto> converter = new DefaultConverter<>();

        final Motorcycle entity = mockData.motorcycle();
        final MotorcycleDto dto= converter.convertToDto(entity, MotorcycleDto.class);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getManufacturer().getId(), dto.getManufacturerId());
        assertEquals(entity.getWeightG(), dto.getWeightG());
    }

    @Test
    public void convertToEntity() {
        final DefaultConverter<Motorcycle, MotorcycleDto> converter = new DefaultConverter<>();

        final MotorcycleDto dto = mockData.motorcycleDto();
        final Motorcycle entity = converter.convertToEntity(dto, Motorcycle.class);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getManufacturerId(), entity.getManufacturer().getId());
        assertEquals(dto.getWeightG(), entity.getWeightG());
    }

}
