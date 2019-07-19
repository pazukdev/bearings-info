package com.pazukdev.backend;

import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.bearing.BearingFactory;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleFactory;
import com.pazukdev.backend.entity.product.oil.OilFactory;
import com.pazukdev.backend.entity.product.seal.SealFactory;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugFactory;
import com.pazukdev.backend.entity.product.unit.engine.EngineFactory;
import lombok.Data;
import org.modelmapper.ModelMapper;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class TestContext {

    private final ModelMapper modelMapper = new ModelMapper();

    private final ServiceContext serviceContext;
    private final ManufacturerFactory manufacturerFactory;
    private final BearingFactory bearingFactory;
    private final SealFactory sealFactory;
    private final SparkPlugFactory sparkPlugFactory;
    private final OilFactory oilFactory;
    private final EngineFactory engineFactory;
    private final MotorcycleFactory motorcycleFactory;

    private final BearingConverter bearingConverter;
    private final UserConverter userConverter;

    public static TestContext create() {
        return new TestContext();
    }

    private TestContext() {
        this.serviceContext = null;
        this.manufacturerFactory = new ManufacturerFactory();
        this.bearingFactory = new BearingFactory(serviceContext, manufacturerFactory);
        this.sealFactory = new SealFactory(serviceContext, manufacturerFactory);
        this.sparkPlugFactory = new SparkPlugFactory(serviceContext, manufacturerFactory);
        this.oilFactory = new OilFactory(serviceContext,manufacturerFactory);
        this.engineFactory = new EngineFactory(serviceContext, manufacturerFactory, bearingFactory, oilFactory, sparkPlugFactory);
        this.motorcycleFactory = new MotorcycleFactory(serviceContext, manufacturerFactory, engineFactory);

        this.bearingConverter = new BearingConverter();
        this.userConverter = new UserConverter(modelMapper);
    }

}
























