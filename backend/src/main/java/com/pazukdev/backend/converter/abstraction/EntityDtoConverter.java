package com.pazukdev.backend.converter.abstraction;

import com.pazukdev.backend.dto.abstraction.AbstractDto;
import com.pazukdev.backend.entity.AbstractEntity;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface EntityDtoConverter<Entity extends AbstractEntity, Dto extends AbstractDto> {

    Dto convertToDto(@NotNull final Entity entity);
    Entity convertToEntity(@NotNull final Dto dto);

    default List<Dto> convertToDtoList(final List<Entity> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

}
