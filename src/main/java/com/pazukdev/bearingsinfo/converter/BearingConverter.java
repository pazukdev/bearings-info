package com.pazukdev.bearingsinfo.converter;

import com.pazukdev.bearingsinfo.dbo.Bearing;
import com.pazukdev.bearingsinfo.dto.BearingDto;
import org.modelmapper.ModelMapper;

/**
 * @author Siarhei Sviarkaltsau
 */
public class BearingConverter implements DboDtoConverter<Bearing, BearingDto> {

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
