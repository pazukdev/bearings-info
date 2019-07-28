package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.dto.ManufacturerDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import com.pazukdev.backend.repository.ManufacturerRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ManufacturerService extends AbstractService<ManufacturerEntity, ManufacturerDto> {

    public ManufacturerService(final ManufacturerRepository repository,
                               final ManufacturerConverter converter) {
        super(repository, converter);
    }

    @Override
    protected ManufacturerEntity findByName(DefaultSearchRequest request) {
        return ((ManufacturerRepository) repository).findByName(request.getName());
    }

}
