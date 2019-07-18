package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.EngineConverter;
import com.pazukdev.backend.dto.product.unit.EngineDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.product.unit.engine.Engine;
import com.pazukdev.backend.repository.EngineRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class EngineService extends AbstractService<Engine, EngineDto> {

    public EngineService(final EngineRepository repository, final EngineConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Engine findByName(DefaultSearchRequest request) {
        return ((EngineRepository) repository).findByName(request.getName());
    }

}
