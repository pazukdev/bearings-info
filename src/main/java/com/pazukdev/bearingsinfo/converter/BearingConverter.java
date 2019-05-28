package com.pazukdev.bearingsinfo.converter;

import com.pazukdev.bearingsinfo.converter.abstraction.EntityDtoConverter;
import com.pazukdev.bearingsinfo.dto.bearing.BearingDto;
import com.pazukdev.bearingsinfo.entity.Bearing;
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
