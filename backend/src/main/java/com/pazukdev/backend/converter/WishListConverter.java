package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.entity.WishListEntity;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
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
        final WishListDto dto = modelMapper.map(wishList, WishListDto.class);
        for (final BearingEntity bearing : wishList.getBearings()) {
            dto.getBearingIds().add(bearing.getId());
        }
        return dto;
    }

    @Override
    public WishListEntity convertToEntity(final WishListDto dto) {
        final WishListEntity wishList = modelMapper.map(dto, WishListEntity.class);
        for (final Long bearingId : dto.getBearingIds()) {
            final BearingEntity bearing = new BearingEntity();
            bearing.setId(bearingId);
            wishList.getBearings().add(bearing);
        }
        return wishList;
    }

}
