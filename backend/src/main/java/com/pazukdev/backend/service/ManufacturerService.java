package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.dto.ManufacturerDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.manufacturer.Manufacturer;
import com.pazukdev.backend.repository.ManufacturerRepository;
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
