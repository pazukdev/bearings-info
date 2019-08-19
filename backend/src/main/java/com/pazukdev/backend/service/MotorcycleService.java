package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.report.FuelReport;
import com.pazukdev.backend.dto.report.ReportFactory;
import com.pazukdev.backend.dto.report.SpeedReport;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class MotorcycleService extends AbstractService<MotorcycleEntity, MotorcycleDto> {

    public MotorcycleService(final MotorcycleRepository repository, final MotorcycleConverter converter) {
        super(repository, converter);
    }

    @Transactional
    @Override
    public List<MotorcycleEntity> findAll() {
        final List<MotorcycleEntity> motorcycles = super.findAll();
        motorcycles.sort(Comparator.comparing(MotorcycleEntity::getProductionStartYear));
        return motorcycles;
    }

    @Transactional
    @Override
    public MotorcycleEntity findByName(final String name) {
        return ((MotorcycleRepository) repository).findByName(name);
    }

    @Transactional
    public TableDto getSpeedReport(final Long motorcycleId) throws ProductNotFoundException {
        final SpeedReport report = ReportFactory.createSpeedReport(getOne(motorcycleId));
        final String maxSpeed = report.getMaxSpeedKmh().toString();
        final String[][] matrix = {{"Max speed, km/h",  maxSpeed}};
        return new TableDto(matrix);
    }

    @Transactional
    public TableDto getFuelReport(final Long motorcycleId) throws ProductNotFoundException {
        final FuelReport report = ReportFactory.createFuelReport(getOne(motorcycleId));
        final String operationalRange = report.getOperationalRangeKm().toString();
        final String[][] matrix = {{"Operational range, km",  operationalRange}};
        return new TableDto(matrix);
    }

}
