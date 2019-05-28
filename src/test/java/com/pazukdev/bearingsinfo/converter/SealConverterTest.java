package com.pazukdev.bearingsinfo.converter;

import com.pazukdev.bearingsinfo.MockData;
import com.pazukdev.bearingsinfo.dto.seal.SealDto;
import com.pazukdev.bearingsinfo.entity.Seal;
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
        final Seal seal = mockData.seal();
        final SealDto dto= converter.convertToDto(seal);

        assertEquals(seal.getName(), dto.getName());
        assertEquals(seal.getRotation(), dto.getRotation());
        assertEquals(seal.getMaterial(), dto.getMaterial());
    }

    @Test
    public void convertToDbo() {
        final SealDto dto = mockData.sealDto();
        final Seal seal = converter.convertToDbo(dto);

        assertEquals(dto.getName(), seal.getName());
        assertEquals(dto.getRotation(), seal.getRotation());
        assertEquals(dto.getMaterial(), seal.getMaterial());
    }
}
