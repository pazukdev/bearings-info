package com.pazukdev.bearingsinfo.dto.abstraction;

import com.pazukdev.bearingsinfo.characteristic.Characteristic;
import com.pazukdev.bearingsinfo.tablemodel.TableModel;
import com.pazukdev.bearingsinfo.tablemodel.TableRow;
import com.pazukdev.bearingsinfo.util.CSVFileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
public abstract class AbstractDtoFactory<Dto extends AbstractDto> {

    public List<Dto> createDtosFromCSVFile() {
        return createDtosFromTableModel(getTableModelFromCSVFile());
    }

    protected abstract String getCSVFilePath();

    protected abstract Dto createDto();

    protected abstract void applyCharacteristics(final Dto dto, final TableRow tableRow);

    protected TableModel getTableModelFromCSVFile() {
        return CSVFileUtil.parse(getCSVFilePath());
    }

    private List<Dto> createDtosFromTableModel(final TableModel tableModel) {
        List<Dto> dtos = new ArrayList<>();

        for (final TableRow tableRow : tableModel.getTableRows()) {
            final Dto dto = createDto(tableRow);
            dtos.add(dto);
        }

        return dtos;
    }

    private Dto createDto(final TableRow tableRow) {
        return getDtoWithAppliedCharacteristics(tableRow);
    }

    private Dto getDtoWithAppliedCharacteristics(final TableRow tableRow) {
        final Dto dto = createDto();
        applyCharacteristics(dto, tableRow);
        return dto;
    }

    protected void applyName(final AbstractDto dto, final TableRow tableRow) {
        final String name = tableRow.getStringValue(Characteristic.NAME);
        dto.setName(name);
    }

}
