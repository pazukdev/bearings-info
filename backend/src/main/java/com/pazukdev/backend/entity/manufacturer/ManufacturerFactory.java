package com.pazukdev.backend.entity.manufacturer;

import com.pazukdev.backend.characteristic.Specification;
import com.pazukdev.backend.entity.AbstractEntityFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Component
public class ManufacturerFactory extends AbstractEntityFactory<ManufacturerEntity> {

    @Override
    protected String getCSVFilePath() {
        return CSVFileUtil.filePath(CSVFileUtil.MANUFACTURER_FILE_NAME);
    }

    @Override
    public ManufacturerEntity createEntity() {
        return new ManufacturerEntity();
    }

    @Override
    protected void applyCharacteristics(ManufacturerEntity manufacturer, TableRow tableRow) {
        super.applyCharacteristics(manufacturer, tableRow);

        applyFounded(manufacturer, tableRow);
        applyDefunct(manufacturer, tableRow);
    }

    private void applyFounded(final ManufacturerEntity manufacturer, final TableRow tableRow) {
        final String year = tableRow.getStringValue(Specification.FOUNDED);
        manufacturer.setFounded(year);
    }

    private void applyDefunct(final ManufacturerEntity manufacturer, final TableRow tableRow) {
        final String year = tableRow.getStringValue(Specification.DEFUNCT);
        manufacturer.setDefunct(year);
    }

}
