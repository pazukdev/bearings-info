package com.pazukdev.backend.entity.product.sparkplug;

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
public class SparkPlugFactory extends ProductFactory<SparkPlug> {

    public SparkPlugFactory(final ServiceContext context, final ManufacturerFactory manufacturerFactory) {
        super(context, manufacturerFactory);
    }

    @Override
    public SparkPlug createEntity() {
        return new SparkPlug();
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.SPARK_PLUG_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(final SparkPlug sparkPlug, final TableRow tableRow) {
        super.applyCharacteristics(sparkPlug, tableRow);
        applyHeatRange(sparkPlug, tableRow);
    }

    private void applyHeatRange(final SparkPlug sparkPlug, final TableRow tableRow) {
        sparkPlug.setHeatRange(tableRow.getIntegerValue(Specification.HEAT_RANGE));
    }
}






















