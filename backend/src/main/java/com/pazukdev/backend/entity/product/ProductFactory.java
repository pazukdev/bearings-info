package com.pazukdev.backend.entity.product;

import com.pazukdev.backend.characteristic.Specification;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.AbstractEntityFactory;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
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

    @Override
    protected void applyCharacteristics(final Entity product, final TableRow tableRow) {
        super.applyCharacteristics(product, tableRow);

        applyProductionStartYear(product, tableRow);
        applyProductionStopYear(product, tableRow);
        applyManufacturer(product, tableRow);
    }

    protected void applyProductionStartYear(final Product product, final TableRow tableRow) {
        final Integer productionStartYear = tableRow.getProductionStartYear(Specification.PRODUCTION);
        product.setProductionStartYear(productionStartYear);
    }

    protected void applyProductionStopYear(final Product product, final TableRow tableRow) {
        final Integer productionStopYear = tableRow.getProductionStopYear(Specification.PRODUCTION);
        product.setProductionStopYear(productionStopYear);
    }

    protected void applyManufacturer(final Product product, final TableRow tableRow) {
        final String manufacturerName = tableRow.getStringValue(Specification.MANUFACTURER);
        final ManufacturerService manufacturerService = context != null ? context.getManufacturerService() : null;
        final ManufacturerEntity manufacturer = getEntity(manufacturerName, manufacturerService, manufacturerFactory);

        product.setManufacturer(manufacturer);
    }

}
