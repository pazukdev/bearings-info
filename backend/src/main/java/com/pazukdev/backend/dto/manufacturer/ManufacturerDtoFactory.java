package com.pazukdev.backend.dto.manufacturer;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class ManufacturerDtoFactory extends AbstractDtoFactory<ManufacturerDto> {

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.manufacturerFile();
    }

    @Override
    public ManufacturerDto createDto() {
        return new ManufacturerDto();
    }

    @Override
    protected void applyCharacteristics(ManufacturerDto dto, TableRow tableRow) {
        applyName(dto, tableRow);
        applyFounded(dto, tableRow);
        applyDefunct(dto, tableRow);
    }

    private void applyFounded(final ManufacturerDto dto, final TableRow tableRow) {
        final String year = tableRow.getStringValue(Characteristic.FOUNDED);
        dto.setFounded(year);
    }

    private void applyDefunct(final ManufacturerDto dto, final TableRow tableRow) {
        final String year = tableRow.getStringValue(Characteristic.DEFUNCT);
        dto.setDefunct(year);
    }

}
