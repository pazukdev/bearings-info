package com.pazukdev.backend.dto.product.sparkplug;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.ProductDtoFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class SparkPlugDtoFactory extends ProductDtoFactory<SparkPlugDto> {

    public SparkPlugDtoFactory(ServiceContext context, ManufacturerDtoFactory manufacturerDtoFactory) {
        super(context, manufacturerDtoFactory);
    }

    @Override
    public SparkPlugDto createDto() {
        return new SparkPlugDto();
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.SPARK_PLUG_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(SparkPlugDto dto, TableRow tableRow) {
        applyName(dto, tableRow);
        applyHeatRange(dto, tableRow);
    }

    private void applyHeatRange(final SparkPlugDto dto, final TableRow tableRow) {
        dto.setHeatRange(tableRow.getIntegerValue(Characteristic.HEAT_RANGE));
    }
}






















