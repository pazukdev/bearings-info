package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.converter.MotorcycleConverter;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.dto.motorcycle.MotorcycleDto;
import com.pazukdev.bearingsinfo.entity.Motorcycle;
import com.pazukdev.bearingsinfo.repository.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class MotorcycleService extends AbstractService<Motorcycle, MotorcycleDto>{

    @Autowired
    public MotorcycleService(final MotorcycleRepository repository,
                             final MotorcycleConverter converter,
                             final AbstractDtoFactory<MotorcycleDto> factory) {
        super(repository, converter, factory);
    }
}
