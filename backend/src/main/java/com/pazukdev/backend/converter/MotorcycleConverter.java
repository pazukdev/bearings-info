package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.bearing.BearingDto;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.product.Motorcycle;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Set;

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
        final MotorcycleDto dto = modelMapper.map(entity, MotorcycleDto.class);
        final Type targetSetType = new TypeToken<Set<BearingDto>>() {}.getType();
        final Set<BearingDto> bearingDtos = modelMapper.map(entity.getBearings(), targetSetType);
        dto.setBearingDtos(bearingDtos);
        return dto;
    }

    @Override
    public Motorcycle convertToEntity(final MotorcycleDto dto) {
        return modelMapper.map(dto, Motorcycle.class);
    }

}


















