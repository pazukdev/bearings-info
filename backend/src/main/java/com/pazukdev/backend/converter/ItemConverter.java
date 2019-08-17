package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.entity.item.ItemDescriptionEntity;
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
        for (final ItemDescriptionEntity description : item.getDescriptions()) {
            itemDto.getDescriptionIds().add(description.getId());
        }
        return itemDto;
    }

    @Override
    public ItemEntity convertToEntity(final ItemDto itemDto) {
        final ItemEntity item = modelMapper.map(itemDto, ItemEntity.class);
        for (final Long itemDescriptionId : itemDto.getDescriptionIds()) {
            final ItemDescriptionEntity descriptionEntity = new ItemDescriptionEntity();
            descriptionEntity.setId(itemDescriptionId);
            item.getDescriptions().add(descriptionEntity);
        }
        return item;
    }

}
