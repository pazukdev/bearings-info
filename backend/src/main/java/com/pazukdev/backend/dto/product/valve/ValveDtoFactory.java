package com.pazukdev.backend.dto.product.valve;

import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.ProductDtoFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ValveDtoFactory extends ProductDtoFactory<ValveDto> {

    public ValveDtoFactory(final ServiceContext context,
                           final ManufacturerDtoFactory manufacturerDtoFactory) {
        super(context, manufacturerDtoFactory);
    }

    @Override
    public ValveDto createDto() {
        return new ValveDto();
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.VALVE_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(ValveDto dto, TableRow tableRow) {

    }
}
