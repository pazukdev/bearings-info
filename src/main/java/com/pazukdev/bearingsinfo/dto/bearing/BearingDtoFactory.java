package com.pazukdev.bearingsinfo.dto.bearing;

import com.pazukdev.bearingsinfo.characteristic.Characteristic;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.tablemodel.TableRow;
import com.pazukdev.bearingsinfo.util.CSVFileUtil;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class BearingDtoFactory extends AbstractDtoFactory<BearingDto> {

    @Override
    protected String getCSVFilePath() {
        return CSVFileUtil.bearingDataFilePath();
    }

    @Override
    protected BearingDto createDto() {
        return new BearingDto();
    }

    @Override
    protected void applyCharacteristics(BearingDto dto, TableRow tableRow) {
        applyName(dto, tableRow);
        applyType(dto, tableRow);
        applyRollingElement(dto, tableRow);
        applyRollingElementsQuantity(dto, tableRow);
    }

    private void applyType(final BearingDto dto, final TableRow tableRow) {
        final String type = tableRow.getStringValue(Characteristic.TYPE);
        dto.setType(type);
    }

    private void applyRollingElement(final BearingDto bearingDto, final TableRow tableRow) {
        final String rollingElementData = tableRow.getStringValueBeforeParenthesises(Characteristic.ROLLING_ELEMENT);
        bearingDto.setRollingElement(rollingElementData);
    }

    private void applyRollingElementsQuantity(final BearingDto dto, final TableRow tableRow) {
        final Integer rollingElementsQuantity = tableRow.getIntegerValue(Characteristic.ROLLING_ELEMENT);
        dto.setRollingElementsQuantity(rollingElementsQuantity);
    }

}
















