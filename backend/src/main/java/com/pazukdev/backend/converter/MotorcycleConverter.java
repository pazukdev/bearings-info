package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class MotorcycleConverter implements EntityDtoConverter<MotorcycleEntity, MotorcycleDto> {

    private final ModelMapper modelMapper;

    public MotorcycleConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MotorcycleDto convertToDto(final MotorcycleEntity entity) {
        return modelMapper.map(entity, MotorcycleDto.class);
    }

    @Override
    public MotorcycleEntity convertToEntity(final MotorcycleDto dto) {
        return modelMapper.map(dto, MotorcycleEntity.class);
    }

}


















