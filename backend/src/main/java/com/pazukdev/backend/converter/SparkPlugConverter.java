package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.SparkPlugDto;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SparkPlugConverter implements EntityDtoConverter<SparkPlugEntity, SparkPlugDto> {

    private final ModelMapper modelMapper;

    public SparkPlugConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SparkPlugDto convertToDto(final SparkPlugEntity entity) {
        return modelMapper.map(entity, SparkPlugDto.class);
    }

    @Override
    public SparkPlugEntity convertToEntity(final SparkPlugDto dto) {
        return modelMapper.map(dto, SparkPlugEntity.class);
    }

}
