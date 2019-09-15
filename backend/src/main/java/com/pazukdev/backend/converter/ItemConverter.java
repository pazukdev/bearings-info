package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.entity.item.Item;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
@Data
public class ItemConverter implements EntityDtoConverter<Item, ItemDto> {

    private final ModelMapper modelMapper;

    @Override
    public ItemDto convertToDto(final Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    @Override
    public Item convertToEntity(final ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }

}