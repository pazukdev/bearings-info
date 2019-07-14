package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.product.Bearing;
import com.pazukdev.backend.entity.product.Motorcycle;
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
        final MotorcycleDto dto = modelMapper.map(entity, MotorcycleDto.class);
        for (final Bearing bearing : entity.getBearings()) {
            dto.getBearingIds().add(bearing.getId());
        }
        return dto;
    }

    @Override
    public Motorcycle convertToEntity(final MotorcycleDto dto) {
        final Motorcycle entity = modelMapper.map(dto, Motorcycle.class);
        for (final Long bearingId : dto.getBearingIds()) {
            final Bearing bearing = new Bearing();
            bearing.setId(bearingId);
            entity.getBearings().add(bearing);
        }
        return entity;
    }

}


















