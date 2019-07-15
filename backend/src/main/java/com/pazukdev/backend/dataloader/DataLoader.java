package com.pazukdev.backend.dataloader;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.AbstractDtoFactory;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.dto.product.bearing.BearingDtoFactory;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDtoFactory;
import com.pazukdev.backend.dto.product.oil.OilDtoFactory;
import com.pazukdev.backend.dto.product.seal.SealDtoFactory;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.service.AbstractService;
import com.pazukdev.backend.service.BearingService;
import com.pazukdev.backend.service.ManufacturerService;
import com.pazukdev.backend.service.MotorcycleService;
import com.pazukdev.backend.service.OilServise;
import com.pazukdev.backend.service.SealService;
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
    private final OilServise oilServise;
    private final ManufacturerDtoFactory manufacturerDtoFactory;
    private final MotorcycleDtoFactory motorcycleDtoFactory;
    private final BearingDtoFactory bearingDtoFactory;
    private final SealDtoFactory sealDtoFactory;
    private final OilDtoFactory oilDtoFactory;

    @Override
    public void run(ApplicationArguments args) {
        populateEmptyTables();
    }

    private void populateEmptyTables() {
        loadManufacturers(manufacturerService.getProductsList().isEmpty());
        loadOils(oilServise.getProductsList().isEmpty());
        loadBearings(bearingService.getProductsList().isEmpty());
        loadSeals(sealService.getProductsList().isEmpty());
        loadMotorcycles(motorcycleService.getProductsList().isEmpty());
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
            createAll(oilDtoFactory, oilServise);
        }
    }

    private <D extends AbstractDto, E extends AbstractEntity> void createAll(final AbstractDtoFactory<D> factory,
                                                                             final AbstractService<E, D> service) {
        factory.createDtosFromCSVFile().forEach(service::create);
    }

}
















