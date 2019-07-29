package com.pazukdev.backend.unit.converter;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.dto.ManufacturerDto;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.dto.VerificationTokenDto;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.product.OilDto;
import com.pazukdev.backend.dto.product.SealDto;
import com.pazukdev.backend.dto.product.SparkPlugDto;
import com.pazukdev.backend.dto.product.ValveDto;
import com.pazukdev.backend.dto.product.unit.EngineDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.VerificationTokenEntity;
import com.pazukdev.backend.entity.WishListEntity;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.entity.product.oil.OilEntity;
import com.pazukdev.backend.entity.product.seal.SealEntity;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugEntity;
import com.pazukdev.backend.entity.product.unit.engine.EngineEntity;
import com.pazukdev.backend.entity.product.valve.ValveEntity;
import org.junit.Test;

import static com.pazukdev.backend.unit.converter.util.ConverterTestUtil.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ConverterTest {

    private final MockData mockData = new MockData();

    @Test
    public void manufacturerToManufacturerDto() {
        final ManufacturerEntity entity = mockData.manufacturer();
        final ManufacturerDto dto = mockData.getTestContext().getManufacturerConverter().convertToDto(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getFounded(), dto.getFounded());
        assertEquals(entity.getDefunct(), dto.getDefunct());
    }

    @Test
    public void manufacturerDtoToManufacturer() {
        final ManufacturerDto dto = mockData.manufacturerDto();
        final ManufacturerEntity entity = mockData.getTestContext().getManufacturerConverter().convertToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getFounded(), entity.getFounded());
        assertEquals(dto.getDefunct(), entity.getDefunct());
    }

    @Test
    public void bearingToBearingDto() {
        final BearingEntity entity = mockData.bearing();
        final BearingDto dto = mockData.getTestContext().getBearingConverter().convertToDto(entity);

        validateBearingConversion(entity, dto);
    }

    @Test
    public void bearingDtoToBearing() {
        final BearingDto dto = mockData.bearingDto();
        final BearingEntity entity = mockData.getTestContext().getBearingConverter().convertToEntity(dto);

        validateBearingConversion(entity, dto);
    }

    @Test
    public void oilToOilDto() {
        final OilEntity entity = new OilEntity();
        final OilDto dto = mockData.getTestContext().getOilConverter().convertToDto(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getBase(), dto.getBase());
        assertEquals(entity.getViscosity(), dto.getViscosity());
        assertEquals(entity.getSeasonality(), dto.getSeasonality());
    }

    @Test
    public void oilDtoToOil() {
        final SealDto dto = mockData.sealDto();
        final SealEntity entity = mockData.getTestContext().getSealConverter().convertToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getRotation(), entity.getRotation());
        assertEquals(dto.getMaterial(), entity.getMaterial());
    }

    @Test
    public void wishListEntityToWishListDto() {
        final WishListEntity entity = new WishListEntity();
        final WishListDto dto = mockData.getTestContext().getWishListConverter().convertToDto(entity);

        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    public void wishListDtoToWishListEntity() {
        final WishListDto dto = new WishListDto();
        final WishListEntity entity = mockData.getTestContext().getWishListConverter().convertToEntity(dto);

        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    public void sparkPlugToSparkPlugDto() {
        final SparkPlugEntity entity = new SparkPlugEntity();
        final SparkPlugDto dto = mockData.getTestContext().getSparkPlugConverter().convertToDto(entity);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getHeatRange(), entity.getHeatRange());
    }

    @Test
    public void sparkPlugDtoToSparkPlug() {
        final SparkPlugDto dto = new SparkPlugDto();
        final SparkPlugEntity entity = mockData.getTestContext().getSparkPlugConverter().convertToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getHeatRange(), entity.getHeatRange());
    }

    @Test
    public void sealToSealDto() {
        final SealEntity entity = mockData.seal();
        final SealDto dto= mockData.getTestContext().getSealConverter().convertToDto(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getRotation(), dto.getRotation());
        assertEquals(entity.getMaterial(), dto.getMaterial());
    }

    @Test
    public void sealDtoToSeal() {
        final SealDto dto = mockData.sealDto();
        final SealEntity entity = mockData.getTestContext().getSealConverter().convertToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getRotation(), entity.getRotation());
        assertEquals(dto.getMaterial(), entity.getMaterial());
    }

    @Test
    public void verificationTokenToVerificationTokenDto() {
        final VerificationTokenEntity entity = new VerificationTokenEntity();
        final VerificationTokenDto dto = mockData.getTestContext().getTokenConverter().convertToDto(entity);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getToken(), entity.getToken());
        assertEquals(dto.getExpiryDate(), entity.getExpiryDate());
    }

    @Test
    public void verificationTokenDtoToVerificationToken() {
        final VerificationTokenDto dto = new VerificationTokenDto();
        final VerificationTokenEntity entity = mockData.getTestContext().getTokenConverter().convertToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getToken(), entity.getToken());
        assertEquals(dto.getExpiryDate(), entity.getExpiryDate());
    }

    @Test
    public void userDtoToUser() {
        final UserDto dto = mockData.userDto();
        final UserEntity user = mockData.getTestContext().getUserConverter().convertToEntity(dto);

        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getPassword(), user.getPassword());
    }

    @Test
    public void valveToValveDto() {
        final ValveEntity entity = new ValveEntity();
        final ValveDto dto = mockData.getTestContext().getValveConverter().convertToDto(entity);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDiameter(), dto.getDiameter());
        assertEquals(entity.getType(), dto.getType());
    }

    @Test
    public void valveDtoToValve() {
        final ValveDto dto = new ValveDto();
        final ValveEntity entity = mockData.getTestContext().getValveConverter().convertToEntity(dto);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDiameter(), dto.getDiameter());
        assertEquals(entity.getType(), dto.getType());
    }

    @Test
    public void userToUserDto() {
        final UserEntity user = mockData.user();
        final UserDto dto = mockData.getTestContext().getUserConverter().convertToDto(user);

        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getPassword(), dto.getPassword());
    }

    @Test
    public void engineToEngineDto() {
        final EngineEntity entity = new EngineEntity();
        final EngineDto dto = mockData.getTestContext().getEngineConverter().convertToDto(entity);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getPowerHp(), entity.getPowerHp());
        assertEquals(dto.getSpeedRpm(), entity.getSpeedRpm());
        assertEquals(dto.getTorqueNm(), entity.getTorqueNm());
    }

    @Test
    public void engineDtoToEngine() {
        final EngineDto dto = new EngineDto();
        final EngineEntity entity = mockData.getTestContext().getEngineConverter().convertToEntity(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getPowerHp(), entity.getPowerHp());
        assertEquals(dto.getSpeedRpm(), entity.getSpeedRpm());
        assertEquals(dto.getTorqueNm(), entity.getTorqueNm());
    }

    @Test
    public void motorcycleToMotorcycleDto() {
        final MotorcycleEntity entity = mockData.motorcycle();
        final MotorcycleDto dto= mockData.getTestContext().getMotorcycleConverter().convertToDto(entity);

        validate(entity, dto);
    }

    @Test
    public void motorcycleDtoToMotorcycleEntity() {
        final MotorcycleDto dto = mockData.motorcycleDto();
        final MotorcycleEntity entity = mockData.getTestContext().getMotorcycleConverter().convertToEntity(dto);

        validate(entity, dto);
    }

    private void validate(final MotorcycleEntity entity, final MotorcycleDto dto) {
        validateAbstractEntityConversion(entity, dto);
        validateAbstractProductConversion(entity, dto);

        assertEquals(entity.getWeightG(), dto.getWeightG());
    }


}











