package com.pazukdev.backend.dto.product.unit.engine;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.bearing.BearingDtoFactory;
import com.pazukdev.backend.dto.product.oil.OilDto;
import com.pazukdev.backend.dto.product.oil.OilDtoFactory;
import com.pazukdev.backend.dto.product.sparkplug.SparkPlugDto;
import com.pazukdev.backend.dto.product.sparkplug.SparkPlugDtoFactory;
import com.pazukdev.backend.dto.product.unit.UnitDtoFactory;
import com.pazukdev.backend.service.BearingService;
import com.pazukdev.backend.service.SparkPlugService;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class EngineDtoFactory extends UnitDtoFactory<EngineDto> {

    private final BearingDtoFactory bearingDtoFactory;
    private final OilDtoFactory oilDtoFactory;
    private final SparkPlugDtoFactory sparkPlugDtoFactory;

    public EngineDtoFactory(final ServiceContext context,
                            final ManufacturerDtoFactory manufacturerDtoFactory,
                            final BearingDtoFactory bearingDtoFactory,
                            final OilDtoFactory oilDtoFactory,
                            final SparkPlugDtoFactory sparkPlugDtoFactory) {
        super(context, manufacturerDtoFactory);
        this.bearingDtoFactory = bearingDtoFactory;
        this.oilDtoFactory = oilDtoFactory;
        this.sparkPlugDtoFactory = sparkPlugDtoFactory;
    }

    @Override
    public EngineDto createDto() {
        return new EngineDto();
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.ENGINE_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(EngineDto dto, TableRow tableRow) {
        applyName(dto, tableRow);
        applyManufacturer(dto, tableRow);
        applyProductionStartYear(dto, tableRow);
        applyProductionStopYear(dto, tableRow);

        applyPower(dto, tableRow);
        applyTorque(dto, tableRow);
        applySpeed(dto, tableRow);
        applySparkPlug(dto, tableRow);
        applyBearings(dto, tableRow);
        applyOils(dto, tableRow);
    }

    private void applyPower(final EngineDto dto, final TableRow tableRow) {
        dto.setPowerHp(tableRow.getIntegerValue(Characteristic.POWER_HP));
    }

    private void applyTorque(final EngineDto dto, final TableRow tableRow) {
        dto.setTorqueNm(tableRow.getIntegerValue(Characteristic.TORQUE_NM));
    }

    private void applySpeed(final EngineDto dto, final TableRow tableRow) {
        dto.setSpeedRpm(tableRow.getIntegerValue(Characteristic.SPEED_RPM));
    }

    private void applySparkPlug(final EngineDto dto, final TableRow tableRow) {
        final String sparkPlugName = tableRow.getStringValue(Characteristic.SPARK_PLUG);
        final SparkPlugService sparkPlugService = context != null ? context.getSparkPlugService() : null;
        final SparkPlugDto sparkPlugDto = getDto(sparkPlugName, sparkPlugService, sparkPlugDtoFactory);

        dto.setSparkPlugId(sparkPlugDto.getId());
    }

    private void applyBearings(final EngineDto dto, final TableRow tableRow) {
        final List<String> bearingNames = tableRow.getStringValues(Characteristic.BEARING);
        final BearingService bearingService = context != null ? context.getBearingService() : null;
        for (final String bearingName : bearingNames) {
            dto.getBearingIds().add(getDto(bearingName, bearingService, bearingDtoFactory).getId());
        }
    }

    private void applyOils(final EngineDto dto, final TableRow tableRow) {
        if (context == null || context.getOilService() == null) {
            return;
        }
        for (final OilDto oilDto : context.getOilService().getProductsList()) {
            dto.getOilIds().add(oilDto.getId());
        }
    }

}




















