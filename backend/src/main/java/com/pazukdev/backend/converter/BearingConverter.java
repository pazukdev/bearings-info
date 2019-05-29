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
    public BearingDto convertToDto(final Bearing bearing) {
        return modelMapper.map(bearing, BearingDto.class);
    }

    @Override
    public Bearing convertToDbo(final BearingDto bearingDto) {
        return modelMapper.map(bearingDto, Bearing.class);
    }

}
