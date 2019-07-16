package com.pazukdev.backend.dto.product.oil;

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
public class OilDtoFactory extends ProductDtoFactory<OilDto> {

    public OilDtoFactory(final ServiceContext context,
                         final ManufacturerDtoFactory manufacturerDtoFactory) {
        super(context, manufacturerDtoFactory);
    }

    @Override
    public OilDto createDto() {
        return new OilDto();
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.OIL_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(OilDto dto, TableRow tableRow) {
        applyName(dto, tableRow);
        applyViscosity(dto, tableRow);
        applyBase(dto, tableRow);
        applySeasonality(dto, tableRow);
    }

    private void applyViscosity(final OilDto dto, final TableRow tableRow) {
        dto.setViscosity(tableRow.getStringValue(Characteristic.VISCOSITY));
    }

    private void applyBase(final OilDto dto, final TableRow tableRow) {
        dto.setBase(tableRow.getStringValue(Characteristic.BASE));
    }

    private void applySeasonality(final OilDto dto, final TableRow tableRow) {
        dto.setSeasonality(tableRow.getStringValue(Characteristic.SEASONALITY));
    }

}



















