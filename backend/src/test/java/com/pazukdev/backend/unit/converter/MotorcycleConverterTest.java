package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.product.Motorcycle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static com.pazukdev.backend.unit.converter.util.ConverterTestUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class MotorcycleConverterTest {

    private MockData mockData = new MockData();
    @InjectMocks
    private MotorcycleConverter converter;
    @Spy
    private ModelMapper modelMapper;

    @Test
    public void convertToDto() {
        final Motorcycle entity = mockData.motorcycle();
        final MotorcycleDto dto= converter.convertToDto(entity);

        validate(entity, dto);
    }

    @Test
    public void convertToEntity() {
        final MotorcycleDto dto = mockData.motorcycleDto();
        final Motorcycle entity = converter.convertToEntity(dto);

        validate(entity, dto);
    }

    private void validate(final Motorcycle entity, final MotorcycleDto dto) {
        validateAbstractEntityConversion(entity, dto);
        validateAbstractProductConversion(entity, dto);

        assertEquals(entity.getWeightG(), dto.getWeightG());

        validateBearingSetConversion(entity.getBearings(), dto.getBearingIds());
    }

}
