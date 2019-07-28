package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.SealConverter;
import com.pazukdev.backend.dto.product.SealDto;
import com.pazukdev.backend.entity.product.seal.SealEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class SealConverterTest {

    private MockData mockData = new MockData();
    private final SealConverter converter = new SealConverter();

    @Test
    public void convertToDto() {
        final SealEntity seal = mockData.seal();
        final SealDto dto= converter.convertToDto(seal);

        assertEquals(seal.getName(), dto.getName());
        assertEquals(seal.getRotation(), dto.getRotation());
        assertEquals(seal.getMaterial(), dto.getMaterial());
    }

    @Test
    public void convertToDbo() {
        final SealDto dto = mockData.sealDto();
        final SealEntity seal = converter.convertToEntity(dto);

        assertEquals(dto.getName(), seal.getName());
        assertEquals(dto.getRotation(), seal.getRotation());
        assertEquals(dto.getMaterial(), seal.getMaterial());
    }
}
