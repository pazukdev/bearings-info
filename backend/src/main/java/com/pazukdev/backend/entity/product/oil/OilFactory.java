package com.pazukdev.backend.entity.product.oil;

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
public class OilFactory extends ProductFactory<Oil> {

    public OilFactory(final ServiceContext context, final ManufacturerFactory manufacturerFactory) {
        super(context, manufacturerFactory);
    }

    @Override
    public Oil createEntity() {
        return new Oil();
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.OIL_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(final Oil oil, final TableRow tableRow) {
        super.applyCharacteristics(oil, tableRow);

        applyViscosity(oil, tableRow);
        applyBase(oil, tableRow);
        applySeasonality(oil, tableRow);
    }

    private void applyViscosity(final Oil oil, final TableRow tableRow) {
        oil.setViscosity(tableRow.getStringValue(Characteristic.VISCOSITY));
    }

    private void applyBase(final Oil oil, final TableRow tableRow) {
        oil.setBase(tableRow.getStringValue(Characteristic.BASE));
    }

    private void applySeasonality(final Oil oil, final TableRow tableRow) {
        oil.setSeasonality(tableRow.getStringValue(Characteristic.SEASONALITY));
    }

}



















