package com.pazukdev.backend.dataloader;

import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.AbstractDtoFactory;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.bearing.BearingDtoFactory;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDtoFactory;
import com.pazukdev.backend.dto.product.oil.OilDtoFactory;
import com.pazukdev.backend.dto.product.seal.SealDtoFactory;
import com.pazukdev.backend.dto.product.sparkplug.SparkPlugDtoFactory;
import com.pazukdev.backend.dto.product.unit.engine.EngineDtoFactory;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.service.AbstractService;
import com.pazukdev.backend.service.BearingService;
import com.pazukdev.backend.service.ManufacturerService;
import com.pazukdev.backend.service.MotorcycleService;
import com.pazukdev.backend.service.OilService;
import com.pazukdev.backend.service.SealService;
import com.pazukdev.backend.service.SparkPlugService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 *
 * the class populates all empty tables in db with default data on app startup
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final ManufacturerService manufacturerService;
    private final MotorcycleService motorcycleService;
    private final BearingService bearingService;
    private final SealService sealService;
    private final OilService oilService;
    private final SparkPlugService sparkPlugService;
    private final ServiceContext serviceContext;

    private final ManufacturerDtoFactory manufacturerDtoFactory;
    private final MotorcycleDtoFactory motorcycleDtoFactory;
    private final BearingDtoFactory bearingDtoFactory;
    private final SealDtoFactory sealDtoFactory;
    private final OilDtoFactory oilDtoFactory;
    private final SparkPlugDtoFactory sparkPlugDtoFactory;
    private final EngineDtoFactory engineDtoFactory;

    @Override
    public void run(ApplicationArguments args) {
        populateEmptyTables();
    }

    private void populateEmptyTables() {
        loadManufacturers(productsListIsEmpty(manufacturerService));
        loadOils(productsListIsEmpty(oilService));
        loadSparkPlugs(productsListIsEmpty(sparkPlugService));
        loadBearings(productsListIsEmpty(bearingService));
        loadSeals(productsListIsEmpty(sealService));
        loadEngines(productsListIsEmpty(serviceContext.getEngineService()));
        loadMotorcycles(productsListIsEmpty(motorcycleService));
    }

    private Boolean productsListIsEmpty(final AbstractService service) {
        return service.getProductsList().isEmpty();
    }

    private void loadManufacturers(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(manufacturerDtoFactory, manufacturerService);
        }
    }

    private void loadMotorcycles(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(motorcycleDtoFactory, motorcycleService);
        }
    }

    private void loadBearings(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(bearingDtoFactory, bearingService);
        }
    }

    private void loadSeals(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(sealDtoFactory, sealService);
        }
    }

    private void loadOils(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(oilDtoFactory, oilService);
        }
    }

    private void loadSparkPlugs(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(sparkPlugDtoFactory, sparkPlugService);
        }
    }

    private void loadEngines(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(engineDtoFactory, serviceContext.getEngineService());
        }
    }

    private <D extends AbstractDto, E extends AbstractEntity> void createAll(final AbstractDtoFactory<D> factory,
                                                                             final AbstractService<E, D> service) {
        factory.createDtosFromCSVFile().forEach(service::create);
    }

}
















