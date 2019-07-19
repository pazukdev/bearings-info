package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.ManufacturerDto;
import com.pazukdev.backend.entity.manufacturer.Manufacturer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class ManufacturerConverter implements EntityDtoConverter<Manufacturer, ManufacturerDto> {

    private final ModelMapper modelMapper;

    public ManufacturerConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ManufacturerDto convertToDto(final Manufacturer entity) {
        final ManufacturerDto dto = modelMapper.map(entity, ManufacturerDto.class);
        return dto;
    }

    @Override
    public Manufacturer convertToEntity(final ManufacturerDto dto) {
        final Manufacturer entity = modelMapper.map(dto, Manufacturer.class);
        return entity;
    }

}
