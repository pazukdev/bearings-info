package com.pazukdev.backend.unit.factory;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.AbstractDtoFactory;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.bearing.BearingDto;
import com.pazukdev.backend.dto.product.bearing.BearingDtoFactory;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDtoFactory;
import com.pazukdev.backend.dto.product.seal.SealDto;
import com.pazukdev.backend.dto.product.seal.SealDtoFactory;
import com.pazukdev.backend.search.DefaultSearchRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class DtoFactoryTest {

    private final MockData mockData = new MockData();

    @Spy
    private ManufacturerDtoFactory manufacturerDtoFactory;
    @InjectMocks
    private MotorcycleDtoFactory motorcycleDtoFactory;
    @Spy
    private BearingDtoFactory bearingDtoFactory = new BearingDtoFactory(null, manufacturerDtoFactory);
    @Spy
    private SealDtoFactory sealDtoFactory = new SealDtoFactory(null, manufacturerDtoFactory);


    @Test
    public void manufacturerDtoFactoryTest() {
        final ManufacturerDto dto = getFirstDtoFromDataFile(new ManufacturerDtoFactory());

        assertEquals("imz", dto.getName());
        assertEquals("1941", dto.getFounded());
        assertNull(dto.getDefunct());
    }

    @Test
    public void motorcycleDtoFactoryTest() {
        final MotorcycleDto dto = getFirstDtoFromDataFile(motorcycleDtoFactory);

        final DefaultSearchRequest request = new DefaultSearchRequest();
        request.setName("imz");

        assertEquals("m-72", dto.getName());
        assertEquals("1941", dto.getProductionStartYear().toString());
        assertEquals("1957", dto.getProductionStopYear().toString());
        assertEquals("300000", dto.getWeightG().toString());
    }

    @Test
    public void bearingDtoFactoryTest() {
        final BearingDto dto = getFirstDtoFromDataFile(bearingDtoFactory);

        assertEquals("209", dto.getName());
        assertNull(dto.getProductionStartYear());
        assertNull(dto.getProductionStopYear());
        assertEquals("deepgroove", dto.getType());
        assertEquals("ball", dto.getRollingElement());
        assertEquals("9", dto.getRollingElementsQuantity().toString());
    }

    @Test
    public void sealDtoFactoryTest() {
        final SealDto dto = getFirstDtoFromDataFile(sealDtoFactory);

        assertEquals("7201191", dto.getName());
        assertNull(dto.getProductionStartYear());
        assertNull(dto.getProductionStopYear());
        assertEquals("left", dto.getRotation());
        assertEquals("rubber", dto.getMaterial());
    }

    private <Dto extends AbstractDto> Dto getFirstDtoFromDataFile(final AbstractDtoFactory<Dto> factory) {
        return factory.createDtosFromCSVFile().get(0);
    }

}
