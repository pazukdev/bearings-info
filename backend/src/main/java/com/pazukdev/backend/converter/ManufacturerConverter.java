package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.ManufacturerDto;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class ManufacturerConverter implements EntityDtoConverter<ManufacturerEntity, ManufacturerDto> {

    private final ModelMapper modelMapper;

    public ManufacturerConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ManufacturerDto convertToDto(final ManufacturerEntity entity) {
        final ManufacturerDto dto = modelMapper.map(entity, ManufacturerDto.class);
        return dto;
    }

    @Override
    public ManufacturerEntity convertToEntity(final ManufacturerDto dto) {
        final ManufacturerEntity entity = modelMapper.map(dto, ManufacturerEntity.class);
        return entity;
    }

}
