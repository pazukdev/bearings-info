package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.converter.BearingConverter;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDto;
import com.pazukdev.bearingsinfo.entity.Bearing;
import com.pazukdev.bearingsinfo.repository.BearingRepository;
import com.pazukdev.bearingsinfo.util.DataFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class BearingService extends AbstractService<Bearing, BearingDto>{

    @Autowired
    public BearingService(final BearingRepository repository,
                          final BearingConverter converter,
                          final AbstractDtoFactory<BearingDto> factory) {
        super(repository, converter, factory);
    }
}
