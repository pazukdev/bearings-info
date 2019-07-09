package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.seal.SealDto;
import com.pazukdev.backend.entity.product.Seal;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SealConverter implements EntityDtoConverter<Seal, SealDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public SealDto convertToDto(final Seal entity) {
        return modelMapper.map(entity, SealDto.class);
    }

    @Override
    public Seal convertToEntity(final SealDto dto) {
        return modelMapper.map(dto, Seal.class);
    }

}
