package com.pazukdev.backend.dto.motorcycle;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.defaultdata.tablemodel.TableRow;
import com.pazukdev.backend.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.backend.util.CSVFileUtil;
import com.pazukdev.backend.util.WeightUtil;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class MotorcycleDtoFactory extends AbstractDtoFactory<MotorcycleDto> {

    @Override
    protected String getCSVFilePath() {
        return CSVFileUtil.motorcycleDataFilePath();
    }

    @Override
    public MotorcycleDto createDto() {
        return new MotorcycleDto();
    }

    @Override
    protected void applyCharacteristics(MotorcycleDto dto, TableRow tableRow) {
        applyName(dto, tableRow);
        applyManufacturer(dto, tableRow);
        applyWeight(dto, tableRow);
    }

    private void applyManufacturer(final MotorcycleDto dto, final TableRow tableRow) {
        final String manufacturer = tableRow.getStringValue(Characteristic.MANUFACTURER);
        dto.setManufacturer(manufacturer);
    }

    private void applyWeight(final MotorcycleDto dto, final TableRow tableRow) {
        final Integer weight_kg = tableRow.getIntegerValue(Characteristic.WEIGHT_KG);
        dto.setWeightG(WeightUtil.toG(weight_kg));
    }

}
