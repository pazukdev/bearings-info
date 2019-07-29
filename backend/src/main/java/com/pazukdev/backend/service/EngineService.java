package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.EngineConverter;
import com.pazukdev.backend.dto.product.unit.EngineDto;
import com.pazukdev.backend.entity.product.unit.engine.EngineEntity;
import com.pazukdev.backend.repository.EngineRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class EngineService extends AbstractService<EngineEntity, EngineDto> {

    public EngineService(final EngineRepository repository, final EngineConverter converter) {
        super(repository, converter);
    }

    @Override
    public EngineEntity findByName(final String name) {
        return ((EngineRepository) repository).findByName(name);
    }

}
