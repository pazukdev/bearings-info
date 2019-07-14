package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.bearing.BearingDto;
import com.pazukdev.backend.entity.product.Bearing;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class BearingConverter implements EntityDtoConverter<Bearing, BearingDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public BearingDto convertToDto(final Bearing entity) {
        final BearingDto dto = modelMapper.map(entity, BearingDto.class);
        return dto;
    }

    @Override
    public Bearing convertToEntity(final BearingDto dto) {
        final Bearing entity = modelMapper.map(dto, Bearing.class);
        return entity;
    }

}
