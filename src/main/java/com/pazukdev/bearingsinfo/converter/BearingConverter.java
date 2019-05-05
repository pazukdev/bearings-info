package com.pazukdev.bearingsinfo.converter;

import com.pazukdev.bearingsinfo.dbo.Bearing;
import com.pazukdev.bearingsinfo.dto.BearingDto;

/**
 * @author Siarhei Sviarkaltsau
 */
public class BearingConverter implements DboDtoConverter<Bearing, BearingDto> {

    @Override
    public BearingDto convertToDto(final Bearing bearing) {
        final BearingDto bearingDto = new BearingDto();
        bearingDto.setId(bearing.getId());
        bearingDto.setName(bearing.getName());
        bearingDto.setMajorLocation(bearing.getMajorLocation());
        bearingDto.setQuantity(bearing.getQuantity());
        return bearingDto;
    }

    @Override
    public Bearing convertToDbo(final BearingDto bearingDto) {
        final Bearing bearing = new Bearing();
        bearing.setId(bearingDto.getId());
        bearing.setName(bearingDto.getName());
        bearing.setMajorLocation(bearingDto.getMajorLocation());
        bearing.setQuantity(bearingDto.getQuantity());
        return bearing;
    }
}
