package com.pazukdev.backend.entity.product.unit;

import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.Product;
import com.pazukdev.backend.entity.product.ProductFactory;

/**
 * @author Siarhei Sviarkaltsau
 */
public abstract class UnitFactory<Entity extends Product> extends ProductFactory<Entity> {

    public UnitFactory(final ServiceContext context, final ManufacturerFactory manufacturerDtoFactory) {
        super(context, manufacturerDtoFactory);
    }

}

















