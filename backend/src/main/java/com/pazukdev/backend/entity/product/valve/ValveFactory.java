package com.pazukdev.backend.entity.product.valve;

import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.ProductFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ValveFactory extends ProductFactory<ValveEntity> {

    public ValveFactory(final ServiceContext context,
                        final ManufacturerFactory manufacturerDtoFactory) {
        super(context, manufacturerDtoFactory);
    }

    @Override
    public ValveEntity createEntity() {
        return new ValveEntity();
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.VALVE_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(final ValveEntity valve, final TableRow tableRow) {
        super.applyCharacteristics(valve, tableRow);
    }
}
