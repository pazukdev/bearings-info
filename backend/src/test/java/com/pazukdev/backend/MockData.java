package com.pazukdev.backend;

import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.dto.bearing.BearingDto;
import com.pazukdev.backend.dto.bearing.BearingDtoFactory;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.motorcycle.MotorcycleDto;
import com.pazukdev.backend.dto.motorcycle.MotorcycleDtoFactory;
import com.pazukdev.backend.dto.seal.SealDto;
import com.pazukdev.backend.dto.seal.SealDtoFactory;
import com.pazukdev.backend.entity.Bearing;
import com.pazukdev.backend.entity.Manufacturer;
import com.pazukdev.backend.entity.Motorcycle;
import com.pazukdev.backend.entity.Seal;
import com.pazukdev.backend.repository.ManufacturerRepository;
import com.pazukdev.backend.service.ManufacturerService;
import lombok.Getter;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Siarhei Sviarkaltsau
 */
@Getter
@RunWith(MockitoJUnitRunner.class)
public class MockData {

    @InjectMocks
    private ManufacturerService manufacturerService;
    @Mock
    private ManufacturerRepository manufacturerRepository;
    @Spy
    private ManufacturerConverter manufacturerConverter;

    private final ManufacturerDtoFactory manufacturerDtoFactory = new ManufacturerDtoFactory();
    private final BearingDtoFactory bearingDtoFactory = new BearingDtoFactory();
    private final MotorcycleDtoFactory motorcycleDtoFactory = new MotorcycleDtoFactory(manufacturerService, manufacturerDtoFactory);
    private final SealDtoFactory sealDtoFactory = new SealDtoFactory();

    public Manufacturer manufacturer() {
        final Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        manufacturer.setFounded("1917");
        manufacturer.setDefunct("1993");
        return manufacturer;
    }

    public ManufacturerDto manufacturerDto() {
        final ManufacturerDto dto = manufacturerDtoFactory.createDto();
        dto.setId(101L);
        dto.setFounded("1914");
        dto.setDefunct("1918");
        return dto;
    }

    public Bearing bearing() {
        final Bearing bearing = new Bearing();
        bearing.setName("bearing name");
        bearing.setType("bearing type");
        bearing.setRollingElement("bearing rolling element");
        bearing.setRollingElementsQuantity(1);
        return bearing;
    }

    public BearingDto bearingDto() {
        final BearingDto bearingDto = bearingDtoFactory.createDto();
        bearingDto.setName("bearingDto name");
        bearingDto.setType("bearingDto type");
        bearingDto.setRollingElement("bearingDto rolling element");
        bearingDto.setRollingElementsQuantity(2);
        return bearingDto;
    }

    public Motorcycle motorcycle() {

        final Motorcycle motorcycle = new Motorcycle();
        motorcycle.setName("motorcycle name");
        motorcycle.setManufacturer(manufacturer());
        motorcycle.setWeightG(350);
        return motorcycle;
    }

    public MotorcycleDto motorcycleDto() {
        final MotorcycleDto motorcycleDto = motorcycleDtoFactory.createDto();
        motorcycleDto.setName("motorcycleDto name");
        motorcycleDto.setManufacturerId(manufacturerDto().getId());
        motorcycleDto.setWeightG(300);
        return motorcycleDto;
    }

    public Seal seal() {
        final Seal seal = new Seal();
        seal.setName("seal name");
        seal.setRotation("left");
        seal.setMaterial("rubber");
        return seal;
    }

    public SealDto sealDto() {
        final SealDto sealDto = sealDtoFactory.createDto();
        sealDto.setName("sealDto name");
        sealDto.setRotation("right");
        sealDto.setMaterial("rubber");
        return sealDto;
    }

}
