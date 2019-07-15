package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.OilConverter;
import com.pazukdev.backend.dto.product.oil.OilDto;
import com.pazukdev.backend.entity.product.Oil;
import com.pazukdev.backend.repository.OilRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;

/**
 * @author Siarhei Sviarkaltsau
 */
public class OilServise extends AbstractService<Oil, OilDto> {

    public OilServise(final OilRepository repository, final OilConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Oil findByName(DefaultSearchRequest request) {
        return ((OilRepository) repository).findByName(request.getName());
    }

}
