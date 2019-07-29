package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.SparkPlugConverter;
import com.pazukdev.backend.dto.product.SparkPlugDto;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugEntity;
import com.pazukdev.backend.repository.SparkPlugRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SparkPlugService extends AbstractService<SparkPlugEntity, SparkPlugDto> {

    public SparkPlugService(final SparkPlugRepository repository, final SparkPlugConverter converter) {
        super(repository, converter);
    }

    @Override
    public SparkPlugEntity findByName(final String name) {
        return ((SparkPlugRepository) repository).findByName(name);
    }

}
