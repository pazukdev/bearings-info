package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.entity.item.TransitiveItem;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
@Data
public class TransitiveItemConverter implements EntityDtoConverter<TransitiveItem, ItemDto> {

    private final ModelMapper modelMapper;

    @Override
    public ItemDto convertToDto(final TransitiveItem item) {
        return modelMapper.map(item, ItemDto.class);
    }

    @Override
    public TransitiveItem convertToEntity(final ItemDto itemDto) {
        return modelMapper.map(itemDto, TransitiveItem.class);
    }

}
