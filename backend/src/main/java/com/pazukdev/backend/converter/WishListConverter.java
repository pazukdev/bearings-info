package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.entity.WishListEntity;
import com.pazukdev.backend.entity.item.ItemEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class WishListConverter implements EntityDtoConverter<WishListEntity, WishListDto> {

    private final ModelMapper modelMapper;

    public WishListConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public WishListDto convertToDto(final WishListEntity wishList) {
        final WishListDto wishListDto = modelMapper.map(wishList, WishListDto.class);
        for (final ItemEntity item : wishList.getItems()) {
            wishListDto.getItemIds().add(item.getId());
        }
        return wishListDto;
    }

    @Override
    public WishListEntity convertToEntity(final WishListDto wishListDto) {
        final WishListEntity wishList = modelMapper.map(wishListDto, WishListEntity.class);
        for (final Long itemId : wishListDto.getItemIds()) {
            final ItemEntity item = new ItemEntity();
            item.setId(itemId);
            wishList.getItems().add(item);
        }
        return wishList;
    }

}
