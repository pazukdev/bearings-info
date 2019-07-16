package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.SparkPlugConverter;
import com.pazukdev.backend.dto.product.sparkplug.SparkPlugDto;
import com.pazukdev.backend.entity.product.SparkPlug;
import com.pazukdev.backend.repository.SparkPlugRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SparkPlugService extends AbstractService<SparkPlug, SparkPlugDto> {

    public SparkPlugService(final SparkPlugRepository repository, final SparkPlugConverter converter) {
        super(repository, converter);
    }

    @Override
    protected SparkPlug findByName(DefaultSearchRequest request) {
        return ((SparkPlugRepository) repository).findByName(request.getName());
    }

}
