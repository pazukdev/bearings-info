package com.pazukdev.backend.entity.product;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.AbstractEntityFactory;
import com.pazukdev.backend.entity.manufacturer.Manufacturer;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.service.ManufacturerService;
import com.pazukdev.backend.tablemodel.TableRow;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ProductFactory<Entity extends Product> extends AbstractEntityFactory<Entity> {

    protected final ServiceContext context;
    protected final ManufacturerFactory manufacturerFactory;

    protected void applyProductionStartYear(final Product product, final TableRow tableRow) {
        final Integer productionStartYear = tableRow.getProductionStartYear(Characteristic.PRODUCTION);
        product.setProductionStartYear(productionStartYear);
    }

    protected void applyProductionStopYear(final Product product, final TableRow tableRow) {
        final Integer productionStopYear = tableRow.getProductionStopYear(Characteristic.PRODUCTION);
        product.setProductionStopYear(productionStopYear);
    }

    protected void applyManufacturer(final Product product, final TableRow tableRow) {
        final String manufacturerName = tableRow.getStringValue(Characteristic.MANUFACTURER);
        final ManufacturerService manufacturerService = context != null ? context.getManufacturerService() : null;
        final Manufacturer manufacturer = getEntity(manufacturerName, manufacturerService, manufacturerFactory);

        product.setManufacturer(manufacturer);
    }

}
