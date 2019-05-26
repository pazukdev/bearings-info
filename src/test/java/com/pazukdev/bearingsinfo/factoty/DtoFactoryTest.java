package com.pazukdev.bearingsinfo.factoty;

import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDto;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDtoFactory;
import com.pazukdev.bearingsinfo.dto.motorcycle.MotorcycleDto;
import com.pazukdev.bearingsinfo.dto.motorcycle.MotorcycleDtoFactory;
import com.pazukdev.bearingsinfo.dto.seal.SealDto;
import com.pazukdev.bearingsinfo.dto.seal.SealDtoFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class DtoFactoryTest {

    @Test
    public void motorcycleDtoFactoryTest() {
        final MotorcycleDto dto = getFirstDtoFromDataFile(new MotorcycleDtoFactory());

        assertEquals("m-72", dto.getName());
        assertEquals("imz", dto.getManufacturer());
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
        return factory.createFromDataFile().get(0);
    }

}
