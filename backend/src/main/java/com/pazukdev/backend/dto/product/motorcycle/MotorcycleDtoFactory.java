package com.pazukdev.backend.dto.product.motorcycle;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.AbstractProductDtoFactory;
import com.pazukdev.backend.dto.product.bearing.BearingDto;
import com.pazukdev.backend.dto.product.bearing.BearingDtoFactory;
import com.pazukdev.backend.service.BearingService;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import com.pazukdev.backend.util.WeightUtil;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Component
public class MotorcycleDtoFactory extends AbstractProductDtoFactory<MotorcycleDto> {

    private final BearingDtoFactory bearingDtoFactory;

    public MotorcycleDtoFactory(ServiceContext context,
                                ManufacturerDtoFactory manufacturerDtoFactory,
                                BearingDtoFactory bearingDtoFactory) {
        super(context, manufacturerDtoFactory);
        this.bearingDtoFactory = bearingDtoFactory;
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.motorcycleFile();
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
        applyBearings(dto, tableRow);
    }

    private void applyWeight(final MotorcycleDto dto, final TableRow tableRow) {
        final Integer weight_kg = tableRow.getIntegerValue(Characteristic.WEIGHT_KG);
        dto.setWeightG(WeightUtil.toG(weight_kg));
    }

    protected void applyBearings(final MotorcycleDto dto, final TableRow tableRow) {
        final List<String> bearingNames = tableRow.getStringValues(Characteristic.BEARING);
        final Set<BearingDto> bearingDtos = new HashSet<>();
        final BearingService bearingService = context != null ? context.getBearingService() : null;
        for (final String bearingName : bearingNames) {
            bearingDtos.add(getDto(bearingName, bearingService, bearingDtoFactory));
        }
        dto.setBearingDtos(bearingDtos);
    }

}







