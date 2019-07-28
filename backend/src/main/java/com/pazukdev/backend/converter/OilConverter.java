package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.OilDto;
import com.pazukdev.backend.entity.product.oil.OilEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class OilConverter implements EntityDtoConverter<OilEntity, OilDto> {

    private final ModelMapper modelMapper;

    public OilConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OilDto convertToDto(final OilEntity entity) {
        return modelMapper.map(entity, OilDto.class);
    }

    @Override
    public OilEntity convertToEntity(final OilDto dto) {
        return modelMapper.map(dto, OilEntity.class);
    }

}
