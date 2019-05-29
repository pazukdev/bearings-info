package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.SealConverter;
import com.pazukdev.backend.dto.seal.SealDto;
import com.pazukdev.backend.entity.Seal;
import com.pazukdev.backend.repository.SealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SealService extends AbstractService<Seal, SealDto>{

    @Autowired
    public SealService(final SealRepository repository,
                       final SealConverter converter) {
        super(repository, converter);
    }

}
