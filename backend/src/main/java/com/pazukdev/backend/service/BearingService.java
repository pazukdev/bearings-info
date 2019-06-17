package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.dto.bearing.BearingDto;
import com.pazukdev.backend.entity.Bearing;
import com.pazukdev.backend.repository.BearingRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class BearingService extends AbstractService<Bearing, BearingDto>{

    public BearingService(final BearingRepository repository,
                          final BearingConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Bearing findByName(DefaultSearchRequest request) {
        return ((BearingRepository) repository).findByName(request.getName());
    }

}
