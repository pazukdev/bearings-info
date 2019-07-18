package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.MotorcycleSpeedReportRS;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.product.motorcycle.Motorcycle;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.repository.MotorcycleRepository;
import com.pazukdev.backend.util.SpeedCalculatorUtil;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class MotorcycleService extends AbstractService<Motorcycle, MotorcycleDto>{

    public MotorcycleService(final MotorcycleRepository repository, final MotorcycleConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Motorcycle findByName(DefaultSearchRequest request) {
        return ((MotorcycleRepository) repository).findByName(request.getName());
    }

    public MotorcycleSpeedReportRS getSpeedReport(final Long motorcycleId) throws ProductNotFoundException {
        return SpeedCalculatorUtil.createSpeedReport(getOne(motorcycleId));
    }

}
