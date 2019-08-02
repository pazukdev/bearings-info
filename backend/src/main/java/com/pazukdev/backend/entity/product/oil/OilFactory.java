package com.pazukdev.backend.entity.product.oil;

import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.ProductFactory;
import com.pazukdev.backend.product.specification.Specification;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class OilFactory extends ProductFactory<OilEntity> {

    public OilFactory(final ServiceContext context, final ManufacturerFactory manufacturerFactory) {
        super(context, manufacturerFactory);
    }

    @Override
    public OilEntity createEntity() {
        return new OilEntity();
    }

    @Override
    protected String getCSVFilePath() {
        return CSVFileUtil.filePath(CSVFileUtil.OIL_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(final OilEntity oil, final TableRow tableRow) {
        super.applyCharacteristics(oil, tableRow);

        applyViscosity(oil, tableRow);
        applyBase(oil, tableRow);
        applySeasonality(oil, tableRow);
    }

    private void applyViscosity(final OilEntity oil, final TableRow tableRow) {
        oil.setViscosity(tableRow.getStringValue(Specification.VISCOSITY));
    }

    private void applyBase(final OilEntity oil, final TableRow tableRow) {
        oil.setBase(tableRow.getStringValue(Specification.BASE));
    }

    private void applySeasonality(final OilEntity oil, final TableRow tableRow) {
        oil.setSeasonality(tableRow.getStringValue(Specification.SEASONALITY));
    }

}



















