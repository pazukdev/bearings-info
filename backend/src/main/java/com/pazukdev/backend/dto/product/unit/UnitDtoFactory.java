package com.pazukdev.backend.dto.product.unit;

import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.ProductDtoFactory;

/**
 * @author Siarhei Sviarkaltsau
 */
public abstract class UnitDtoFactory<Dto extends UnitDto> extends ProductDtoFactory<Dto> {

    public UnitDtoFactory(final ServiceContext context, final ManufacturerDtoFactory manufacturerDtoFactory) {
        super(context, manufacturerDtoFactory);
    }

}

















