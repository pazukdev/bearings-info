package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.entity.Manufacturer;
import com.pazukdev.backend.repository.ManufacturerRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ManufacturerService extends AbstractService<Manufacturer, ManufacturerDto>{

    public ManufacturerService(final ManufacturerRepository repository, final ManufacturerConverter converter) {
        super(repository, converter);
    }

    @Transactional
    public ManufacturerDto search(final DefaultSearchRequest request) {
        final Manufacturer entity = ((ManufacturerRepository) repository).findByName(request.getName());
        return converter.convertToDto(entity);
    }

}
