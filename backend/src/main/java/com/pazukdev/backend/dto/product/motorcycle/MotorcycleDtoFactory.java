package com.pazukdev.backend.dto.product.motorcycle;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.ProductDtoFactory;
import com.pazukdev.backend.dto.product.unit.engine.EngineDto;
import com.pazukdev.backend.dto.product.unit.engine.EngineDtoFactory;
import com.pazukdev.backend.service.EngineService;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import com.pazukdev.backend.util.WeightUtil;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class MotorcycleDtoFactory extends ProductDtoFactory<MotorcycleDto> {

    private final EngineDtoFactory engineDtoFactory;

    public MotorcycleDtoFactory(final ServiceContext context,
                                final ManufacturerDtoFactory manufacturerDtoFactory,
                                final EngineDtoFactory engineDtoFactory) {
        super(context, manufacturerDtoFactory);
        this.engineDtoFactory = engineDtoFactory;
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.MOTORCYCLE_FILE_NAME);
    }

    @Override
    public MotorcycleDto createDto() {
        return new MotorcycleDto();
    }

    @Override
    protected void applyCharacteristics(MotorcycleDto dto, TableRow tableRow) {
        applyName(dto, tableRow);
        applyManufacturer(dto, tableRow);
        applyProductionStartYear(dto, tableRow);
        applyProductionStopYear(dto, tableRow);

        applyWeight(dto, tableRow);
        applyEngine(dto, tableRow);
    }

    private void applyEngine(final MotorcycleDto dto, final TableRow tableRow) {
        final String engineName = tableRow.getStringValue(Characteristic.ENGINE);
        final EngineService engineService = context != null ? context.getEngineService() : null;
        final EngineDto engineDto = getDto(engineName, engineService, engineDtoFactory);

        dto.setEngineId(engineDto.getId());
    }

    private void applyWeight(final MotorcycleDto dto, final TableRow tableRow) {
        final Integer weight_kg = tableRow.getIntegerValue(Characteristic.WEIGHT_KG);
        dto.setWeightG(WeightUtil.toG(weight_kg));
    }

}








