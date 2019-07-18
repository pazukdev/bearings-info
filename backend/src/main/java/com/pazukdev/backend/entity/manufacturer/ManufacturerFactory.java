package com.pazukdev.backend.entity.manufacturer;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.entity.AbstractEntityFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Component
public class ManufacturerFactory extends AbstractEntityFactory<Manufacturer> {

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.MANUFACTURER_FILE_NAME);
    }

    @Override
    public Manufacturer createEntity() {
        return new Manufacturer();
    }

    @Override
    protected void applyCharacteristics(Manufacturer manufacturer, TableRow tableRow) {
        super.applyCharacteristics(manufacturer, tableRow);

        applyFounded(manufacturer, tableRow);
        applyDefunct(manufacturer, tableRow);
    }

    private void applyFounded(final Manufacturer manufacturer, final TableRow tableRow) {
        final String year = tableRow.getStringValue(Characteristic.FOUNDED);
        manufacturer.setFounded(year);
    }

    private void applyDefunct(final Manufacturer manufacturer, final TableRow tableRow) {
        final String year = tableRow.getStringValue(Characteristic.DEFUNCT);
        manufacturer.setDefunct(year);
    }

}
