package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.product.Motorcycle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static com.pazukdev.backend.unit.converter.util.ConverterTestUtil.validateBearingSetConversion;
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

    @Before
    public void init() {

    }

    @Test
    public void convertToDto() {
        final Motorcycle entity = mockData.motorcycle();
        final MotorcycleDto dto= converter.convertToDto(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getProductionStartYear(), dto.getProductionStartYear());
        assertEquals(entity.getProductionStopYear(), dto.getProductionStopYear());
        assertEquals(entity.getManufacturer().getId(), dto.getManufacturerId());
        assertEquals(entity.getWeightG(), dto.getWeightG());

        validateBearingSetConversion(entity.getBearings(), dto.getBearingDtos());
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
