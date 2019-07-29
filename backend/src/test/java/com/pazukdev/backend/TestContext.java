package com.pazukdev.backend;

import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.converter.EngineConverter;
import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.converter.OilConverter;
import com.pazukdev.backend.converter.SealConverter;
import com.pazukdev.backend.converter.SparkPlugConverter;
import com.pazukdev.backend.converter.TokenConverter;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.converter.WishListConverter;
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

    private final UserConverter userConverter;
    private final TokenConverter tokenConverter;
    private final WishListConverter wishListConverter;
    private final ManufacturerConverter manufacturerConverter;
    private final BearingConverter bearingConverter;
    private final SealConverter sealConverter;
    private final OilConverter oilConverter;
    private final SparkPlugConverter sparkPlugConverter;
    private final EngineConverter engineConverter;
    private final MotorcycleConverter motorcycleConverter;


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

        this.userConverter = new UserConverter(modelMapper);
        this.tokenConverter = new TokenConverter(modelMapper);
        this.wishListConverter = new WishListConverter(modelMapper);
        this.manufacturerConverter = new ManufacturerConverter(modelMapper);
        this.bearingConverter = new BearingConverter();
        this.sealConverter = new SealConverter();
        this.oilConverter = new OilConverter(modelMapper);
        this.sparkPlugConverter = new SparkPlugConverter(modelMapper);
        this.engineConverter = new EngineConverter(modelMapper);
        this.motorcycleConverter = new MotorcycleConverter(modelMapper);
    }

}
























