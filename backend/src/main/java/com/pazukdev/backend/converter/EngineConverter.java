package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.unit.engine.EngineDto;
import com.pazukdev.backend.entity.product.Bearing;
import com.pazukdev.backend.entity.product.Oil;
import com.pazukdev.backend.entity.product.unit.Engine;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class EngineConverter implements EntityDtoConverter<Engine, EngineDto> {

    private final ModelMapper modelMapper;

    public EngineConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EngineDto convertToDto(final Engine entity) {
        final EngineDto dto = modelMapper.map(entity, EngineDto.class);
        for (final Bearing bearing : entity.getBearings()) {
            dto.getBearingIds().add(bearing.getId());
        }
        for (final Oil oil : entity.getOils()) {
            dto.getOilIds().add(oil.getId());
        }
        return dto;
    }

    @Override
    public Engine convertToEntity(final EngineDto dto) {
        final Engine entity = modelMapper.map(dto, Engine.class);
        for (final Long bearingId : dto.getBearingIds()) {
            final Bearing bearing = new Bearing();
            bearing.setId(bearingId);
            entity.getBearings().add(bearing);
        }
        for (final Long oilId : dto.getOilIds()) {
            final Oil oil = new Oil();
            oil.setId(oilId);
            entity.getOils().add(oil);
        }
        return entity;
    }

}
