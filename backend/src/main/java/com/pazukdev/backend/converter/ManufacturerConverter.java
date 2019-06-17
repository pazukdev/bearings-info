package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.entity.Manufacturer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ManufacturerConverter implements EntityDtoConverter<Manufacturer, ManufacturerDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ManufacturerDto convertToDto(final Manufacturer entity) {
        return modelMapper.map(entity, ManufacturerDto.class);
    }

    @Override
    public Manufacturer convertToEntity(final ManufacturerDto dto) {
        return modelMapper.map(dto, Manufacturer.class);
    }

}
