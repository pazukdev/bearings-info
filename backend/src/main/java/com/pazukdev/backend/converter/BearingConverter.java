package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.bearing.BearingDto;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.product.Bearing;
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
public class BearingConverter implements EntityDtoConverter<Bearing, BearingDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public BearingDto convertToDto(final Bearing entity) {
        final BearingDto dto = modelMapper.map(entity, BearingDto.class);
        final Type targetSetType = new TypeToken<Set<MotorcycleDto>>() {}.getType();
        final Set<MotorcycleDto> motorcycleDtos = modelMapper.map(entity.getMotorcycles(), targetSetType);
        dto.setMotorcycles(motorcycleDtos);
        return dto;
    }

    @Override
    public Bearing convertToEntity(final BearingDto dto) {
        final Bearing entity = modelMapper.map(dto, Bearing.class);
        final Type targetSetType = new TypeToken<Set<Motorcycle>>() {}.getType();
        final Set<Motorcycle> motorcycles = modelMapper.map(dto.getMotorcycles(), targetSetType);
        entity.setMotorcycles(motorcycles);
        return entity;
    }

}
