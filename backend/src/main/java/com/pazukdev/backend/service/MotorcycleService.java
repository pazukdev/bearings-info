package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.report.FuelReport;
import com.pazukdev.backend.dto.report.ReportFactory;
import com.pazukdev.backend.dto.report.SpeedReport;
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

    public ItemDto getSpeedReport(final Long motorcycleId) throws ProductNotFoundException {
        final SpeedReport report = ReportFactory.createSpeedReport(getOne(motorcycleId));
        final String description = "Max speed, km/h: " + report.getMaxSpeedKmh();
        return new ItemDto(description);
    }

    public ItemDto getFuelReport(final Long motorcycleId) throws ProductNotFoundException {
        final FuelReport report = ReportFactory.createFuelReport(getOne(motorcycleId));
        final String description = "Operational range, km: " + report.getOperationalRangeKm();
        return new ItemDto(description);
    }

}
