package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class BearingConverter implements EntityDtoConverter<BearingEntity, BearingDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public BearingDto convertToDto(final BearingEntity entity) {
        final BearingDto dto = modelMapper.map(entity, BearingDto.class);
        return dto;
    }

    @Override
    public BearingEntity convertToEntity(final BearingDto dto) {
        final BearingEntity entity = modelMapper.map(dto, BearingEntity.class);
        return entity;
    }

}
