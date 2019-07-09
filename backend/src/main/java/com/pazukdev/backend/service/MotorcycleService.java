package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.product.Motorcycle;
import com.pazukdev.backend.repository.MotorcycleRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class MotorcycleService extends AbstractService<Motorcycle, MotorcycleDto>{

    public MotorcycleService(final MotorcycleRepository repository,
                             final MotorcycleConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Motorcycle findByName(DefaultSearchRequest request) {
        return ((MotorcycleRepository) repository).findByName(request.getName());
    }

}
