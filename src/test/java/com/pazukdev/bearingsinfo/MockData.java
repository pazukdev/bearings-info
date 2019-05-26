package com.pazukdev.bearingsinfo;

import com.pazukdev.bearingsinfo.dto.motorcycle.MotorcycleDto;
import com.pazukdev.bearingsinfo.dto.seal.SealDto;
import com.pazukdev.bearingsinfo.entity.Bearing;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDto;
import com.pazukdev.bearingsinfo.entity.Motorcycle;
import com.pazukdev.bearingsinfo.entity.Seal;

/**
 * @author Siarhei Sviarkaltsau
 */
public class MockData {

    public static Bearing bearing() {
        final Bearing bearing = new Bearing();
        bearing.setName("bearing name");
        bearing.setType("bearing type");
        bearing.setRollingElement("bearing rolling element");
        bearing.setRollingElementsQuantity(1);
        return bearing;
    }

    public static BearingDto bearingDto() {
        final BearingDto bearingDto = new BearingDto();
        bearingDto.setName("bearingDto name");
        bearingDto.setType("bearingDto type");
        bearingDto.setRollingElement("bearingDto rolling element");
        bearingDto.setRollingElementsQuantity(2);
        return bearingDto;
    }

    public static Motorcycle motorcycle() {
        final Motorcycle motorcycle = new Motorcycle();
        motorcycle.setName("motorcycle name");
        motorcycle.setManufacturer("motorcycle manufacturer");
        motorcycle.setWeightG(350);
        return motorcycle;
    }

    public static MotorcycleDto motorcycleDto() {
        final MotorcycleDto motorcycleDto = new MotorcycleDto();
        motorcycleDto.setName("motorcycleDto name");
        motorcycleDto.setManufacturer("motorcycleDto manufacturer");
        motorcycleDto.setWeightG(300);
        return motorcycleDto;
    }

    public static Seal seal() {
        final Seal seal = new Seal();
        seal.setName("seal name");
        seal.setRotation("left");
        seal.setMaterial("rubber");
        return seal;
    }

    public static SealDto sealDto() {
        final SealDto sealDto = new SealDto();
        sealDto.setName("sealDto name");
        sealDto.setRotation("right");
        sealDto.setMaterial("rubber");
        return sealDto;
    }

}
