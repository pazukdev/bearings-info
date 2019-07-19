package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.entity.WishList;
import com.pazukdev.backend.entity.product.bearing.Bearing;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class WishListConverter implements EntityDtoConverter<WishList, WishListDto> {

    private final ModelMapper modelMapper;

    public WishListConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public WishListDto convertToDto(final WishList wishList) {
        final WishListDto dto = modelMapper.map(wishList, WishListDto.class);
        for (final Bearing bearing : wishList.getBearings()) {
            dto.getBearingIds().add(bearing.getId());
        }
        return dto;
    }

    @Override
    public WishList convertToEntity(final WishListDto dto) {
        final WishList wishList = modelMapper.map(dto, WishList.class);
        for (final Long bearingId : dto.getBearingIds()) {
            final Bearing bearing = new Bearing();
            bearing.setId(bearingId);
            wishList.getBearings().add(bearing);
        }
        return wishList;
    }

}
