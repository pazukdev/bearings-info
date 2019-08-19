package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.entity.item.ItemEntity;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
@Data
public class ItemConverter implements EntityDtoConverter<ItemEntity, ItemDto> {

    private final ModelMapper modelMapper;

    @Override
    public ItemDto convertToDto(final ItemEntity item) {
        return modelMapper.map(item, ItemDto.class);
    }

    @Override
    public ItemEntity convertToEntity(final ItemDto itemDto) {
        return modelMapper.map(itemDto, ItemEntity.class);
    }

}
