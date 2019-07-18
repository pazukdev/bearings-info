package com.pazukdev.backend.entity.product.unit;

import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.ProductFactory;
import com.pazukdev.backend.tablemodel.TableRow;

/**
 * @author Siarhei Sviarkaltsau
 */
public abstract class UnitFactory<Entity extends Unit> extends ProductFactory<Entity> {

    public UnitFactory(final ServiceContext context, final ManufacturerFactory manufacturerDtoFactory) {
        super(context, manufacturerDtoFactory);
    }

    @Override
    protected void applyCharacteristics(final Entity unit, final TableRow tableRow) {
        super.applyCharacteristics(unit, tableRow);
    }
}

















