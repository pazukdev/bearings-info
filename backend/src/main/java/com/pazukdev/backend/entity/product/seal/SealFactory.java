package com.pazukdev.backend.entity.product.seal;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.ProductFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class SealFactory extends ProductFactory<Seal> {

    public SealFactory(final ServiceContext context, final ManufacturerFactory manufacturerFactory) {
        super(context, manufacturerFactory);
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.SEAL_FILE_NAME);
    }

    @Override
    public Seal createEntity() {
        return new Seal();
    }

    @Override
    protected void applyCharacteristics(final Seal seal, final TableRow tableRow) {
        super.applyCharacteristics(seal, tableRow);

        applyRotation(seal, tableRow);
        applyMaterial(seal, tableRow);
    }

    private void applyRotation(final Seal seal, TableRow tableRow) {
        final String rotation = tableRow.getStringValue(Characteristic.ROTATION);
        seal.setRotation(rotation);
    }

    private void applyMaterial(final Seal seal, final TableRow tableRow) {
        final String material = tableRow.getStringValue(Characteristic.MATERIAL);
        seal.setMaterial(material);
    }

}
