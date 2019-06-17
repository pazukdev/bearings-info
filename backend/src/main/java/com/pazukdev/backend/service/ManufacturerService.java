package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.entity.Manufacturer;
import com.pazukdev.backend.repository.ManufacturerRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ManufacturerService extends AbstractService<Manufacturer, ManufacturerDto> {

    public ManufacturerService(final ManufacturerRepository repository,
                               final ManufacturerConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Manufacturer findByName(DefaultSearchRequest request) {
        return ((ManufacturerRepository) repository).findByName(request.getName());
    }

}
