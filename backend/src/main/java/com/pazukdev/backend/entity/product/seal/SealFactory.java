package com.pazukdev.backend.entity.product.seal;

import com.pazukdev.backend.characteristic.Specification;
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
public class SealFactory extends ProductFactory<SealEntity> {

    public SealFactory(final ServiceContext context, final ManufacturerFactory manufacturerFactory) {
        super(context, manufacturerFactory);
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.SEAL_FILE_NAME);
    }

    @Override
    public SealEntity createEntity() {
        return new SealEntity();
    }

    @Override
    protected void applyCharacteristics(final SealEntity seal, final TableRow tableRow) {
        super.applyCharacteristics(seal, tableRow);

        applyRotation(seal, tableRow);
        applyMaterial(seal, tableRow);
    }

    private void applyRotation(final SealEntity seal, TableRow tableRow) {
        final String rotation = tableRow.getStringValue(Specification.ROTATION);
        seal.setRotation(rotation);
    }

    private void applyMaterial(final SealEntity seal, final TableRow tableRow) {
        final String material = tableRow.getStringValue(Specification.MATERIAL);
        seal.setMaterial(material);
    }

}
