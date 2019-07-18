package com.pazukdev.backend.config;

import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.bearing.BearingFactory;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleFactory;
import com.pazukdev.backend.entity.product.seal.SealFactory;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@Component
public class FactoryContext {

    private final ManufacturerFactory manufacturerDtoFactory;
    private final BearingFactory bearingDtoFactory;
    private final SealFactory sealDtoFactory;
    private final MotorcycleFactory motorcycleDtoFactory;

}
