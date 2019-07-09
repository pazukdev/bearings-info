package com.pazukdev.backend.dto;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.tablemodel.TableModel;
import com.pazukdev.backend.tablemodel.TableModelFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public abstract class AbstractDtoFactory<Dto extends AbstractDto> {

    public List<Dto> createDtosFromCSVFile() {
        return createDtosFromTableModel(getTableModelFromCSVFile());
    }

    public abstract Dto createDto();

    protected abstract File getCSVFile();

    protected abstract void applyCharacteristics(final Dto dto, final TableRow tableRow);

    public Dto searchByName(final String name) {
        return createDtosFromCSVFile().stream().filter(dto -> dto.getName().equals(name)).findFirst().orElse(null);
    }

    private TableModel getTableModelFromCSVFile() {
        final TableModelFactory factory = TableModelFactory.create();
        return factory.createTableModel(getCSVFile());
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
