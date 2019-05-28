package com.pazukdev.bearingsinfo.dto.seal;

import com.pazukdev.bearingsinfo.characteristic.Characteristic;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.tablemodel.TableRow;
import com.pazukdev.bearingsinfo.util.CSVFileUtil;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class SealDtoFactory extends AbstractDtoFactory<SealDto> {

    @Override
    protected String getCSVFilePath() {
        return CSVFileUtil.sealDataFilePath();
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
