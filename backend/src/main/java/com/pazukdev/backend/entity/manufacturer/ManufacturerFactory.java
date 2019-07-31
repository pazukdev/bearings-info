package com.pazukdev.backend.entity.manufacturer;

import com.pazukdev.backend.characteristic.Specification;
import com.pazukdev.backend.entity.AbstractEntityFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Component
public class ManufacturerFactory extends AbstractEntityFactory<ManufacturerEntity> {

    @Override
    protected File getCSVFile() {
        //CSVFileUtil.file(CSVFileUtil.MANUFACTURER_FILE_NAME);
        InputStream in = getClass().getResourceAsStream("/static/manufacturer.csv");
        File file = new File("manufacturer.csv");
        try {
            FileUtils.copyInputStreamToFile(in, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
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
