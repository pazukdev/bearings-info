package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.converter.SealConverter;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.dto.seal.SealDto;
import com.pazukdev.bearingsinfo.entity.Seal;
import com.pazukdev.bearingsinfo.repository.SealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SealService extends AbstractService<Seal, SealDto>{

    @Autowired
    public SealService(final SealRepository repository,
                       final SealConverter converter,
                       final AbstractDtoFactory<SealDto> factory) {
        super(repository, converter, factory);
    }

}
