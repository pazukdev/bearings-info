package com.pazukdev.bearingsinfo.defaultdata;

import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDtoFactory;
import com.pazukdev.bearingsinfo.dto.motorcycle.MotorcycleDtoFactory;
import com.pazukdev.bearingsinfo.dto.seal.SealDtoFactory;
import com.pazukdev.bearingsinfo.entity.AbstractEntity;
import com.pazukdev.bearingsinfo.service.AbstractService;
import com.pazukdev.bearingsinfo.service.BearingService;
import com.pazukdev.bearingsinfo.service.MotorcycleService;
import com.pazukdev.bearingsinfo.service.SealService;
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

    private final MotorcycleService motorcycleService;
    private final BearingService bearingService;
    private final SealService sealService;
    private final MotorcycleDtoFactory motorcycleDtoFactory;
    private final BearingDtoFactory bearingDtoFactory;
    private final SealDtoFactory sealDtoFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        populateEmptyTables();
    }

    private void populateEmptyTables() {
        loadMotorcycles(motorcycleService.getProductsList().isEmpty());
        loadBearings(bearingService.getProductsList().isEmpty());
        loadSeals(sealService.getProductsList().isEmpty());
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

    private <D extends AbstractDto, E extends AbstractEntity> void createAll(final AbstractDtoFactory<D> factory,
                                                                             final AbstractService<E, D> service) {
        factory.createDtosFromCSVFile().forEach(service::create);
    }

}
