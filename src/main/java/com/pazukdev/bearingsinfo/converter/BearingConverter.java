package com.pazukdev.bearingsinfo.converter;

import com.pazukdev.bearingsinfo.dbo.Bearing;
import com.pazukdev.bearingsinfo.dto.BearingDto;

/**
 * @author Siarhei Sviarkaltsau
 */
public class BearingConverter implements DboDtoConverter<Bearing, BearingDto> {

    @Override
    public BearingDto convertToDto(final Bearing bearing) {
        return null;
    }

    @Override
    public Bearing convertToDbo(final BearingDto bearingDTO) {
        return null;
    }
}
