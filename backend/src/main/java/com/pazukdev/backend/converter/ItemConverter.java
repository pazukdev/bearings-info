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
        final ItemDto itemDto = modelMapper.map(item, ItemDto.class);
//        for (final ItemEntity childItem : item.getItems()) {
//            itemDto.getItemIds().add(childItem.getId());
//        }
        return itemDto;
    }

    @Override
    public ItemEntity convertToEntity(final ItemDto itemDto) {
        final ItemEntity item = modelMapper.map(itemDto, ItemEntity.class);
//        for (final Long childItemId : itemDto.getItemIds()) {
//            final ItemEntity childItem = new ItemEntity();
//            childItem.setId(childItemId);
//            item.getItems().add(childItem);
//        }
        return item;
    }

}
