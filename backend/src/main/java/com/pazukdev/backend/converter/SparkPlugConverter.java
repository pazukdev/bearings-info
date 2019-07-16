package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.product.sparkplug.SparkPlugDto;
import com.pazukdev.backend.entity.product.SparkPlug;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SparkPlugConverter implements EntityDtoConverter<SparkPlug, SparkPlugDto> {

    private final ModelMapper modelMapper;

    public SparkPlugConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SparkPlugDto convertToDto(final SparkPlug entity) {
        return modelMapper.map(entity, SparkPlugDto.class);
    }

    @Override
    public SparkPlug convertToEntity(final SparkPlugDto dto) {
        return modelMapper.map(dto, SparkPlug.class);
    }

}
