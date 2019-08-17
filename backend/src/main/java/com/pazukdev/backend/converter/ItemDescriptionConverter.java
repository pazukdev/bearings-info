package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.item.ItemDescriptionDto;
import com.pazukdev.backend.entity.item.ItemDescriptionEntity;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
@Data
public class ItemDescriptionConverter implements EntityDtoConverter<ItemDescriptionEntity, ItemDescriptionDto> {

    private final ModelMapper modelMapper;

    @Override
    public ItemDescriptionDto convertToDto(final ItemDescriptionEntity entity) {
        return modelMapper.map(entity, ItemDescriptionDto.class);
    }

    @Override
    public ItemDescriptionEntity convertToEntity(final ItemDescriptionDto dto) {
        return modelMapper.map(dto, ItemDescriptionEntity.class);
    }

}
