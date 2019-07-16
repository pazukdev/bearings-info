package com.pazukdev.backend.config;

import com.pazukdev.backend.service.BearingService;
import com.pazukdev.backend.service.EngineService;
import com.pazukdev.backend.service.ManufacturerService;
import com.pazukdev.backend.service.MotorcycleService;
import com.pazukdev.backend.service.OilService;
import com.pazukdev.backend.service.SealService;
import com.pazukdev.backend.service.SparkPlugService;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@Component
public class ServiceContext {

    private final ManufacturerService manufacturerService;
    private final BearingService bearingService;
    private final SealService sealService;
    private final OilService oilService;
    private final SparkPlugService sparkPlugService;
    private final EngineService engineService;
    private final MotorcycleService motorcycleService;

}
