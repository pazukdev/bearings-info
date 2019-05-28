package com.pazukdev.bearingsinfo.converter;

import com.pazukdev.bearingsinfo.converter.abstraction.EntityDtoConverter;
import com.pazukdev.bearingsinfo.dto.seal.SealDto;
import com.pazukdev.bearingsinfo.entity.Seal;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SealConverter implements EntityDtoConverter<Seal, SealDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public SealDto convertToDto(final Seal seal) {
        return modelMapper.map(seal, SealDto.class);
    }

    @Override
    public Seal convertToDbo(final SealDto sealDto) {
        return modelMapper.map(sealDto, Seal.class);
    }

}
