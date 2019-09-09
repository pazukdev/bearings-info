package com.pazukdev.backend;

import com.pazukdev.backend.dto.ManufacturerDto;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.dto.product.*;
import com.pazukdev.backend.dto.product.unit.EngineDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.entity.product.oil.OilEntity;
import com.pazukdev.backend.entity.product.seal.SealEntity;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugEntity;
import com.pazukdev.backend.entity.product.unit.engine.EngineEntity;
import lombok.Getter;

import java.util.*;

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
        dto.setId(1L);
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
        dto.setId(1L);
        dto.setName("login");
        dto.setPassword("password");
        return dto;
    }

    public UserEntity user() {
        final UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("login");
        user.setPassword("password");
        return user;
    }

    public OilEntity oil() {
        final OilEntity oil = new OilEntity();
        oil.setId(1L);
        oil.setName("20w-50");
        oil.setViscosity("20w-50");
        oil.setBase("mineral");
        oil.setSeasonality("multigrade");
        return oil;
    }

    public OilDto oilDto() {
        final OilDto dto = new OilDto();
        dto.setId(1L);
        dto.setName("20w-50");
        dto.setViscosity("20w-50");
        dto.setBase("mineral");
        dto.setSeasonality("multigrade");
        return dto;
    }

    public SparkPlugEntity sparkPlug() {
        final SparkPlugEntity sparkPlug = new SparkPlugEntity();
        sparkPlug.setId(1L);
        sparkPlug.setName("a14");
        sparkPlug.setHeatRange(14);
        return sparkPlug;
    }

    public SparkPlugDto sparkPlugDto() {
        final SparkPlugDto dto = new SparkPlugDto();
        dto.setId(1L);
        dto.setName("a14");
        dto.setHeatRange(14);
        return dto;
    }

    public EngineEntity engine() {
        final EngineEntity engine = new EngineEntity();
        engine.setId(1L);
        engine.setName("MT-8");
        engine.setManufacturer(manufacturer());
        engine.setPowerHp(32);
        engine.setTorqueNm(40);
        engine.setSpeedRpm(5900);
        engine.setSparkPlug(sparkPlug());
        engine.setBearings(bearings());
        return engine;
    }

    public EngineDto engineDto() {
        final EngineDto dto = new EngineDto();
        dto.setId(1L);
        dto.setName("MT-8");
        dto.setManufacturerId(1L);
        dto.setPowerHp(32);
        dto.setTorqueNm(40);
        dto.setSpeedRpm(5900);
        dto.setSparkPlugId(1L);
        dto.setBearingIds(new HashSet<>(Arrays.asList(1L, 2L)));
        return dto;
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
        final MotorcycleDto dto = new MotorcycleDto();
        dto.setId(1L);
        dto.setName("motorcycleDto name");
        dto.setProductionStartYear(1939);
        dto.setProductionStopYear(1946);
        dto.setManufacturerId(manufacturerDto().getId());
        dto.setWeightG(300);
        dto.setFuelCapacityL(20D);
        return dto;
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
        final SealDto dto = new SealDto();
        dto.setId(id);
        dto.setName(name);
        dto.setRotation("right");
        dto.setMaterial("rubber");
        return dto;
    }

//    public WishListEntity wishList() {
//        final WishListEntity wishList = new WishListEntity();
//        wishList.setId(1L);
//        wishList.setBearings(bearings());
//        return wishList;
//    }
//
//    public WishListDto wishListDto() {
//        final WishListDto dto = new WishListDto();
//        dto.setId(1L);
//        dto.setBearingIds(new HashSet<>(Arrays.asList(1L, 2L)));
//        return dto;
//    }

    public List<ItemEntity> itemsList() {
        final ItemEntity item1 = new ItemEntity();
        item1.setCategory("first category");
        final ItemEntity item2 = new ItemEntity();
        item2.setCategory("second category");
        final ItemEntity item3 = new ItemEntity();
        item3.setCategory("second category");
        final ItemEntity item4 = new ItemEntity();
        item4.setCategory("second category");
        final ItemEntity item5 = new ItemEntity();
        item5.setCategory("second category");
        final ItemEntity item6 = new ItemEntity();
        item6.setCategory("third category");
        return new ArrayList<>(Arrays.asList(item1, item2, item3, item4, item5, item6));
    }

    public ItemEntity item(final String category, final String name, final Integer quantity) {
        final ItemEntity item = new ItemEntity();
        item.setCategory(category);
        item.setName(name);
        return item;
    }

}


















