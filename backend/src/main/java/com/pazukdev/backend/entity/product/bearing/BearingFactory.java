package com.pazukdev.backend.entity.product.bearing;

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
public class BearingFactory extends ProductFactory<Bearing> {

    public BearingFactory(final ServiceContext context, final ManufacturerFactory manufacturerFactory) {
        super(context, manufacturerFactory);
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.BEARING_FILE_NAME);
    }

    @Override
    public Bearing createEntity() {
        return new Bearing();
    }

    @Override
    protected void applyCharacteristics(final Bearing bearing, final TableRow tableRow) {
        super.applyCharacteristics(bearing, tableRow);

        applyType(bearing, tableRow);
        applyRollingElement(bearing, tableRow);
        applyRollingElementsQuantity(bearing, tableRow);
    }

    private void applyType(final Bearing bearing, final TableRow tableRow) {
        final String type = tableRow.getStringValue(Characteristic.TYPE);
        bearing.setType(type);
    }

    private void applyRollingElement(final Bearing bearing, final TableRow tableRow) {
        final String rollingElementData = tableRow.getStringValueBeforeParenthesises(Characteristic.ROLLING_ELEMENT);
        bearing.setRollingElement(rollingElementData);
    }

    private void applyRollingElementsQuantity(final Bearing bearing, final TableRow tableRow) {
        final Integer rollingElementsQuantity = tableRow.getIntegerValue(Characteristic.ROLLING_ELEMENT);
        bearing.setRollingElementsQuantity(rollingElementsQuantity);
    }

}
















