package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
@Data
public class BearingConverter implements EntityDtoConverter<BearingEntity, BearingDto> {

    private final ModelMapper modelMapper;

    @Override
    public BearingDto convertToDto(final BearingEntity entity) {
        return modelMapper.map(entity, BearingDto.class);
    }

    @Override
    public BearingEntity convertToEntity(final BearingDto dto) {
        return modelMapper.map(dto, BearingEntity.class);
    }

}
