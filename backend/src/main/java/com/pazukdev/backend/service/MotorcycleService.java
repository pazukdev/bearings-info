package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.report.FuelReportRS;
import com.pazukdev.backend.dto.report.ReportFactory;
import com.pazukdev.backend.dto.report.SpeedReportRS;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class MotorcycleService extends AbstractService<MotorcycleEntity, MotorcycleDto>{

    public MotorcycleService(final MotorcycleRepository repository, final MotorcycleConverter converter) {
        super(repository, converter);
    }

    @Override
    public MotorcycleEntity findByName(final String name) {
        return ((MotorcycleRepository) repository).findByName(name);
    }

    public SpeedReportRS getSpeedReport(final Long motorcycleId) throws ProductNotFoundException {
        return ReportFactory.createSpeedReport(getOne(motorcycleId));
    }

    public FuelReportRS getFuelReport(final Long motorcycleId) throws ProductNotFoundException {
        return ReportFactory.createFuelReport(getOne(motorcycleId));
    }

}
