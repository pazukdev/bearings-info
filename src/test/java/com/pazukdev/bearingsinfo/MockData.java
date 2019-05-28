package com.pazukdev.bearingsinfo;

import com.pazukdev.bearingsinfo.dto.bearing.BearingDto;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDtoFactory;
import com.pazukdev.bearingsinfo.dto.motorcycle.MotorcycleDto;
import com.pazukdev.bearingsinfo.dto.motorcycle.MotorcycleDtoFactory;
import com.pazukdev.bearingsinfo.dto.seal.SealDto;
import com.pazukdev.bearingsinfo.dto.seal.SealDtoFactory;
import com.pazukdev.bearingsinfo.entity.Bearing;
import com.pazukdev.bearingsinfo.entity.Motorcycle;
import com.pazukdev.bearingsinfo.entity.Seal;

/**
 * @author Siarhei Sviarkaltsau
 */
public class MockData {

    private final BearingDtoFactory bearingDtoFactory = new BearingDtoFactory();
    private final MotorcycleDtoFactory motorcycleDtoFactory = new MotorcycleDtoFactory();
    private final SealDtoFactory sealDtoFactory = new SealDtoFactory();

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
        motorcycle.setManufacturer("motorcycle manufacturer");
        motorcycle.setWeightG(350);
        return motorcycle;
    }

    public MotorcycleDto motorcycleDto() {
        final MotorcycleDto motorcycleDto = motorcycleDtoFactory.createDto();
        motorcycleDto.setName("motorcycleDto name");
        motorcycleDto.setManufacturer("motorcycleDto manufacturer");
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
