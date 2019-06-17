package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.abstraction.AbstractDto;
import com.pazukdev.backend.entity.AbstractEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class DefaultConverter<Entity extends AbstractEntity, Dto extends AbstractDto>
        implements EntityDtoConverter<Entity, Dto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    private volatile Class<Entity> entityClass;
    private volatile Class<Dto> dtoClass;

    public Dto convertToDto(final Entity entity, final Class<Dto> dtoClass) {
        this.dtoClass = dtoClass;
        final Dto dto = convertToDto(entity);
        this.dtoClass = null;
        return dto;
    }

    public Entity convertToEntity(final Dto dto, final Class<Entity> entityClass) {
        this.entityClass = entityClass;
        final Entity entity = convertToEntity(dto);
        this.entityClass = null;
        return entity;
    }

    @Override
    public Dto convertToDto(final Entity entity) {
        return modelMapper.map(entity, dtoClass);
    }

    @Override
    public Entity convertToEntity(final Dto dto) {
        return modelMapper.map(dto, entityClass);
    }

}
