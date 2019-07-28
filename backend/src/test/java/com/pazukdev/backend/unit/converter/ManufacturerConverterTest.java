package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.dto.ManufacturerDto;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ManufacturerConverterTest {

    private final MockData mockData = new MockData();
    private final ManufacturerConverter converter = new ManufacturerConverter(mockData.modelMapper());

    @Test
    public void convertToDto() {
        final ManufacturerEntity entity = mockData.manufacturer();
        final ManufacturerDto dto = converter.convertToDto(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getFounded(), dto.getFounded());
        assertEquals(entity.getDefunct(), dto.getDefunct());
    }

    @Test
    public void convertToEntity() {
        final ManufacturerDto dto = mockData.manufacturerDto();
        final ManufacturerEntity entity = converter.convertToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getFounded(), entity.getFounded());
        assertEquals(dto.getDefunct(), entity.getDefunct());
    }

}
