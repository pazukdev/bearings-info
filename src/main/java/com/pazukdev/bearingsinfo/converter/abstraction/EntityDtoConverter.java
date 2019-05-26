package com.pazukdev.bearingsinfo.converter.abstraction;

import com.pazukdev.bearingsinfo.entity.AbstractEntity;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface EntityDtoConverter<Entity extends AbstractEntity, Dto extends AbstractDto> {

    Dto convertToDto(@NotNull final Entity dbo);
    Entity convertToDbo(@NotNull final Dto dto);

    default List<Dto> convertToDtoList(final List<Entity> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

}
