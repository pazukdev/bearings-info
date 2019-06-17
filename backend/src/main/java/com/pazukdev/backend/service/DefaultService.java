package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.DefaultConverter;
import com.pazukdev.backend.dto.abstraction.AbstractDto;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.repository.DefaultRepository;
import com.pazukdev.backend.search.DefaultSearchRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class DefaultService<Entity extends AbstractEntity, Dto extends AbstractDto>
        extends AbstractService<Entity, Dto>{


    public DefaultService(DefaultRepository<Entity> repository, DefaultConverter<Entity, Dto> converter) {
        super(repository, converter);
    }

    @Transactional
    public Dto search(final DefaultSearchRequest request) {
        final Entity entity = ((DefaultRepository<Entity>) repository).findByName(request.getName());
        return converter.convertToDto(entity);
    }

}
