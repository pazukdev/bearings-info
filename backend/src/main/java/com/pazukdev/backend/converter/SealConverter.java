package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.SealDto;
import com.pazukdev.backend.entity.product.seal.SealEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SealConverter implements EntityDtoConverter<SealEntity, SealDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public SealDto convertToDto(final SealEntity entity) {
        return modelMapper.map(entity, SealDto.class);
    }

    @Override
    public SealEntity convertToEntity(final SealDto dto) {
        return modelMapper.map(dto, SealEntity.class);
    }

}
