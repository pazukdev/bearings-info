package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.Motorcycle;
import com.pazukdev.backend.repository.MotorcycleRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
