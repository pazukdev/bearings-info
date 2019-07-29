package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.SealConverter;
import com.pazukdev.backend.dto.product.SealDto;
import com.pazukdev.backend.entity.product.seal.SealEntity;
import com.pazukdev.backend.repository.SealRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SealService extends AbstractService<SealEntity, SealDto>{

    public SealService(final SealRepository repository,
                       final SealConverter converter) {
        super(repository, converter);
    }

    @Override
    public SealEntity findByName(final String name) {
        return ((SealRepository) repository).findByName(name);
    }

}
