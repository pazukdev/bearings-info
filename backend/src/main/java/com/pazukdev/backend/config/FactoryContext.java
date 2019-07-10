package com.pazukdev.backend.config;

import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.bearing.BearingDtoFactory;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDtoFactory;
import com.pazukdev.backend.dto.product.seal.SealDtoFactory;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@Component
public class FactoryContext {

    private final ManufacturerDtoFactory manufacturerDtoFactory;
    private final BearingDtoFactory bearingDtoFactory;
    private final SealDtoFactory sealDtoFactory;
    private final MotorcycleDtoFactory motorcycleDtoFactory;

}
