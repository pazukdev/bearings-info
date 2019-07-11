package com.pazukdev.backend;

import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.dto.product.bearing.BearingDto;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
import com.pazukdev.backend.dto.product.seal.SealDto;
import com.pazukdev.backend.entity.Manufacturer;
import com.pazukdev.backend.entity.product.Bearing;
import com.pazukdev.backend.entity.product.Motorcycle;
import com.pazukdev.backend.entity.product.Seal;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Getter
public class MockData {

    public Manufacturer manufacturer() {
        final Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        manufacturer.setFounded("1917");
        manufacturer.setDefunct("1993");
        return manufacturer;
    }

    public ManufacturerDto manufacturerDto() {
        final ManufacturerDto dto = new ManufacturerDto();
        dto.setId(101L);
        dto.setFounded("1914");
        dto.setDefunct("1918");
        return dto;
    }

    public Bearing bearing() {
        return bearing(99L, "bearing name");
    }

    public Bearing bearing(final Long id, final String name) {
        final Bearing bearing = new Bearing();
        bearing.setId(id);
        bearing.setName(name);
        bearing.setType("bearing type");
        bearing.setRollingElement("bearing rolling element");
        bearing.setRollingElementsQuantity(1);
        bearing.setMotorcycles(new HashSet<>(Arrays.asList(new Motorcycle(), new Motorcycle())));
        return bearing;
    }

    public Set<Bearing> bearings() {
        final Set<Bearing> entities = new HashSet<>();
        entities.add(bearing(11L, "bearing 1"));
        entities.add(bearing(12L, "bearing 2"));
        entities.add(bearing(13L, "bearing 3"));
        return entities;
    }

    public BearingDto bearingDto() {
        return bearingDto(11L, "bearingDto name");
    }

    public BearingDto bearingDto(final Long id, final String name) {
        final BearingDto bearingDto = new BearingDto();
        bearingDto.setId(id);
        bearingDto.setName(name);
        bearingDto.setType("bearingDto type");
        bearingDto.setRollingElement("bearingDto rolling element");
        bearingDto.setRollingElementsQuantity(2);
        bearingDto.getMotorcycles().add(new MotorcycleDto());
        return bearingDto;
    }

    public Set<BearingDto> bearingDtos() {
        final Set<BearingDto> dtos = new HashSet<>();
        dtos.add(bearingDto(111L, "bearing dto 1"));
        dtos.add(bearingDto(112L, "bearing dto 2"));
        dtos.add(bearingDto(113L, "bearing dto 3"));
        return dtos;
    }

    public Motorcycle motorcycle() {
        final Motorcycle motorcycle = new Motorcycle();
        motorcycle.setName("motorcycle name");
        motorcycle.setProductionStartYear(1917);
        motorcycle.setProductionStopYear(1993);
        motorcycle.setManufacturer(manufacturer());
        motorcycle.setWeightG(350);
        motorcycle.setBearings(bearings());
        return motorcycle;
    }

    public MotorcycleDto motorcycleDto() {
        final MotorcycleDto motorcycleDto = new MotorcycleDto();
        motorcycleDto.setName("motorcycleDto name");
        motorcycleDto.setProductionStartYear(1939);
        motorcycleDto.setProductionStopYear(1946);
        motorcycleDto.setManufacturerId(manufacturerDto().getId());
        motorcycleDto.setWeightG(300);
        motorcycleDto.setBearingDtos(bearingDtos());
        return motorcycleDto;
    }

    public Seal seal() {
        return seal(100L, "seal name");
    }

    public Seal seal(final Long id, final String name) {
        final Seal seal = new Seal();
        seal.setId(id);
        seal.setName(name);
        seal.setRotation("left");
        seal.setMaterial("rubber");
        return seal;
    }

    public SealDto sealDto() {
        return sealDto(101L, "sealDto name");
    }

    public SealDto sealDto(final Long id, final String name) {
        final SealDto sealDto = new SealDto();
        sealDto.setId(id);
        sealDto.setName(name);
        sealDto.setRotation("right");
        sealDto.setMaterial("rubber");
        return sealDto;
    }

}
