package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.entity.product.motorcycle.Motorcycle;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class MotorcycleConverter implements EntityDtoConverter<Motorcycle, MotorcycleDto> {

    private final ModelMapper modelMapper;

    public MotorcycleConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MotorcycleDto convertToDto(final Motorcycle entity) {
        return modelMapper.map(entity, MotorcycleDto.class);
    }

    @Override
    public Motorcycle convertToEntity(final MotorcycleDto dto) {
        return modelMapper.map(dto, Motorcycle.class);
    }

}


















