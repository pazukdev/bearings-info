package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import com.pazukdev.backend.repository.BearingRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class BearingService extends AbstractService<BearingEntity, BearingDto>{

    public BearingService(final BearingRepository repository, final BearingConverter converter) {
        super(repository, converter);
    }

    @Override
    protected BearingEntity findByName(DefaultSearchRequest request) {
        return ((BearingRepository) repository).findByName(request.getName());
    }

}
