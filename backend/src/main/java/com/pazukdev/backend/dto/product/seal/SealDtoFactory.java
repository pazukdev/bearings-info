package com.pazukdev.backend.dto.product.seal;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.dto.product.AbstractProductDtoFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class SealDtoFactory extends AbstractProductDtoFactory<SealDto> {

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.sealFile();
    }

    @Override
    public SealDto createDto() {
        return new SealDto();
    }

    @Override
    protected void applyCharacteristics(SealDto dto, TableRow tableRow) {
        applyName(dto, tableRow);
        applyRotation(dto, tableRow);
        applyMaterial(dto, tableRow);
    }

    private void applyRotation(final SealDto dto, TableRow tableRow) {
        final String rotation = tableRow.getStringValue(Characteristic.ROTATION);
        dto.setRotation(rotation);
    }

    private void applyMaterial(final SealDto dto, final TableRow tableRow) {
        final String material = tableRow.getStringValue(Characteristic.MATERIAL);
        dto.setMaterial(material);
    }

}
