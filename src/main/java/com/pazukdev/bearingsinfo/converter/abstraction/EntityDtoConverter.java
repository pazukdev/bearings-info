package com.pazukdev.bearingsinfo.converter.abstraction;

import com.pazukdev.bearingsinfo.entity.AbstractEntity;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;

import javax.validation.constraints.NotNull;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface EntityDtoConverter<Entity extends AbstractEntity, Dto extends AbstractDto> {

    Dto convertToDto(@NotNull final Entity dbo);
    Entity convertToDbo(@NotNull final Dto dto);

}
