package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.OilConverter;
import com.pazukdev.backend.dto.product.OilDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.product.oil.OilEntity;
import com.pazukdev.backend.repository.OilRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class OilService extends AbstractService<OilEntity, OilDto> {

    public OilService(final OilRepository repository, final OilConverter converter) {
        super(repository, converter);
    }

    @Override
    protected OilEntity findByName(DefaultSearchRequest request) {
        return ((OilRepository) repository).findByName(request.getName());
    }

}
