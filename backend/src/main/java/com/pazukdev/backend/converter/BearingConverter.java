package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.bearing.BearingDto;
import com.pazukdev.backend.entity.Bearing;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class BearingConverter implements EntityDtoConverter<Bearing, BearingDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public BearingDto convertToDto(final Bearing entity) {
        return modelMapper.map(entity, BearingDto.class);
    }

    @Override
    public Bearing convertToEntity(final BearingDto dto) {
        return modelMapper.map(dto, Bearing.class);
    }

}
