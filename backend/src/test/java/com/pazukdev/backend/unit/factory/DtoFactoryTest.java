package com.pazukdev.backend.unit.factory;

import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.dto.abstraction.AbstractDto;
import com.pazukdev.backend.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.backend.dto.bearing.BearingDto;
import com.pazukdev.backend.dto.bearing.BearingDtoFactory;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.motorcycle.MotorcycleDto;
import com.pazukdev.backend.dto.motorcycle.MotorcycleDtoFactory;
import com.pazukdev.backend.dto.seal.SealDto;
import com.pazukdev.backend.dto.seal.SealDtoFactory;
import com.pazukdev.backend.repository.ManufacturerRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;
import com.pazukdev.backend.service.ManufacturerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class DtoFactoryTest {

    @InjectMocks
    private ManufacturerService service;
    @Mock
    private ManufacturerRepository repository;
    @Spy
    private ManufacturerConverter converter;

    @Test
    public void manufacturerDtoFactoryTest() {
        final ManufacturerDto dto = getFirstDtoFromDataFile(new ManufacturerDtoFactory());

        assertEquals("imz", dto.getName());
        assertEquals("1941", dto.getFounded());
        assertEquals("?", dto.getDefunct());
    }

    @Test
    public void motorcycleDtoFactoryTest() {
        final MotorcycleDto dto = getFirstDtoFromDataFile(new MotorcycleDtoFactory(service));

        final DefaultSearchRequest request = new DefaultSearchRequest();
        request.setName("imz");

        assertEquals("m-72", dto.getName());
        assertEquals("imz", service.search(request).getName());
        assertEquals("300000", dto.getWeightG().toString());
    }

    @Test
    public void bearingDtoFactoryTest() {
        final BearingDto dto = getFirstDtoFromDataFile(new BearingDtoFactory());

        assertEquals("209", dto.getName());
        assertEquals("deepgroove", dto.getType());
        assertEquals("ball", dto.getRollingElement());
        assertEquals("9", dto.getRollingElementsQuantity().toString());
    }

    @Test
    public void sealDtoFactoryTest() {
        final SealDto dto = getFirstDtoFromDataFile(new SealDtoFactory());

        assertEquals("7201191", dto.getName());
        assertEquals("left", dto.getRotation());
        assertEquals("rubber", dto.getMaterial());
    }

    private <Dto extends AbstractDto> Dto getFirstDtoFromDataFile(final AbstractDtoFactory<Dto> factory) {
        return factory.createDtosFromCSVFile().get(0);
    }

}
