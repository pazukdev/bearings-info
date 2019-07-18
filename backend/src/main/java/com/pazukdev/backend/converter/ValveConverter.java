package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.ValveDto;
import com.pazukdev.backend.entity.product.valve.Valve;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ValveConverter implements EntityDtoConverter<Valve, ValveDto> {

    private final ModelMapper modelMapper;

    public ValveConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ValveDto convertToDto(final Valve entity) {
        return modelMapper.map(entity, ValveDto.class);
    }

    @Override
    public Valve convertToEntity(final ValveDto dto) {
        return modelMapper.map(dto, Valve.class);
    }

}
