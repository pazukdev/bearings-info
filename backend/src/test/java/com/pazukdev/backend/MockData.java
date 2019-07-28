package com.pazukdev.backend;

import com.pazukdev.backend.dto.ManufacturerDto;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.product.SealDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.entity.product.seal.SealEntity;
import lombok.Getter;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;

import static com.pazukdev.backend.util.CSVFileUtil.getFirstEntity;

/**
 * @author Siarhei Sviarkaltsau
 */
@Getter
public class MockData {

    private final TestContext testContext = TestContext.create();

    public ManufacturerEntity manufacturer() {
        final ManufacturerEntity manufacturer = new ManufacturerEntity();
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

    public BearingEntity bearing() {
        return bearing(99L, "bearing name");
    }

    public BearingEntity bearing(final Long id, final String name) {
        final BearingEntity bearing = new BearingEntity();
        bearing.setId(id);
        bearing.setName(name);
        bearing.setType("bearing type");
        bearing.setRollingElement("bearing rolling element");
        bearing.setRollingElementsQuantity(1);
        return bearing;
    }

    public Set<BearingEntity> bearings() {
        final Set<BearingEntity> entities = new HashSet<>();
        entities.add(bearing(11L, "bearing 1"));
        entities.add(bearing(12L, "bearing 2"));
        entities.add(bearing(13L, "bearing 3"));
        return entities;
    }

    public BearingDto bearingDto() {
        return bearingDto(11L, "bearingDto name");
    }

    public UserDto userDto() {
        final UserDto dto = new UserDto();
        dto.setName("login");
        dto.setPassword("password");
        return dto;
    }

    public UserEntity user() {
        final UserEntity user = new UserEntity();
        user.setName("login");
        user.setPassword("password");
        return user;
    }

    public BearingDto bearingDto(final Long id, final String name) {
        final BearingDto bearingDto = new BearingDto();
        bearingDto.setId(id);
        bearingDto.setName(name);
        bearingDto.setType("bearingDto type");
        bearingDto.setRollingElement("bearingDto rolling element");
        bearingDto.setRollingElementsQuantity(2);
        return bearingDto;
    }

    public Set<BearingDto> bearingDtos() {
        final Set<BearingDto> dtos = new HashSet<>();
        dtos.add(bearingDto(111L, "bearing dto 1"));
        dtos.add(bearingDto(112L, "bearing dto 2"));
        dtos.add(bearingDto(113L, "bearing dto 3"));
        return dtos;
    }

    public MotorcycleEntity motorcycle() {
        return getFirstEntity(testContext.getMotorcycleFactory());
    }

    public MotorcycleDto motorcycleDto() {
        final MotorcycleDto motorcycleDto = new MotorcycleDto();
        motorcycleDto.setName("motorcycleDto name");
        motorcycleDto.setProductionStartYear(1939);
        motorcycleDto.setProductionStopYear(1946);
        motorcycleDto.setManufacturerId(manufacturerDto().getId());
        motorcycleDto.setWeightG(300);
        motorcycleDto.setFuelCapacityL(20D);
        return motorcycleDto;
    }

    public SealEntity seal() {
        return seal(100L, "seal name");
    }

    public SealEntity seal(final Long id, final String name) {
        final SealEntity seal = new SealEntity();
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

    public ModelMapper modelMapper() {
        return testContext.getModelMapper();
    }

}
