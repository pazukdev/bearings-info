package com.pazukdev.backend.entity.product.sparkplug;

import com.pazukdev.backend.characteristic.Specification;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.ProductFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class SparkPlugFactory extends ProductFactory<SparkPlugEntity> {

    public SparkPlugFactory(final ServiceContext context, final ManufacturerFactory manufacturerFactory) {
        super(context, manufacturerFactory);
    }

    @Override
    public SparkPlugEntity createEntity() {
        return new SparkPlugEntity();
    }

    @Override
    protected String getCSVFilePath() {
        return CSVFileUtil.filePath(CSVFileUtil.SPARK_PLUG_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(final SparkPlugEntity sparkPlug, final TableRow tableRow) {
        super.applyCharacteristics(sparkPlug, tableRow);
        applyHeatRange(sparkPlug, tableRow);
    }

    private void applyHeatRange(final SparkPlugEntity sparkPlug, final TableRow tableRow) {
        sparkPlug.setHeatRange(tableRow.getIntegerValue(Specification.HEAT_RANGE));
    }
}






















