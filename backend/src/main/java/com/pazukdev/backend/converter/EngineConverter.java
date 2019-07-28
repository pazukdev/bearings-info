package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.unit.EngineDto;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import com.pazukdev.backend.entity.product.oil.OilEntity;
import com.pazukdev.backend.entity.product.unit.engine.EngineEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class EngineConverter implements EntityDtoConverter<EngineEntity, EngineDto> {

    private final ModelMapper modelMapper;

    public EngineConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EngineDto convertToDto(final EngineEntity entity) {
        final EngineDto dto = modelMapper.map(entity, EngineDto.class);
        for (final BearingEntity bearing : entity.getBearings()) {
            dto.getBearingIds().add(bearing.getId());
        }
        for (final OilEntity oil : entity.getOils()) {
            dto.getOilIds().add(oil.getId());
        }
        return dto;
    }

    @Override
    public EngineEntity convertToEntity(final EngineDto dto) {
        final EngineEntity entity = modelMapper.map(dto, EngineEntity.class);
        for (final Long bearingId : dto.getBearingIds()) {
            final BearingEntity bearing = new BearingEntity();
            bearing.setId(bearingId);
            entity.getBearings().add(bearing);
        }
        for (final Long oilId : dto.getOilIds()) {
            final OilEntity oil = new OilEntity();
            oil.setId(oilId);
            entity.getOils().add(oil);
        }
        return entity;
    }

}
