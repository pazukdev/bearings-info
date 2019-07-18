package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.SparkPlugConverter;
import com.pazukdev.backend.dto.product.SparkPlugDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlug;
import com.pazukdev.backend.repository.SparkPlugRepository;
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
