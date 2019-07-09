package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.SealConverter;
import com.pazukdev.backend.dto.product.seal.SealDto;
import com.pazukdev.backend.entity.product.Seal;
import com.pazukdev.backend.repository.SealRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SealService extends AbstractService<Seal, SealDto>{

    public SealService(final SealRepository repository,
                       final SealConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Seal findByName(DefaultSearchRequest request) {
        return ((SealRepository) repository).findByName(request.getName());
    }

}
