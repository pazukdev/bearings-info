package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.motorcycle.MotorcycleDto;
import com.pazukdev.backend.entity.Motorcycle;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class MotorcycleConverter implements EntityDtoConverter<Motorcycle, MotorcycleDto> {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public MotorcycleDto convertToDto(final Motorcycle motorcycle) {
        return modelMapper.map(motorcycle, MotorcycleDto.class);
    }

    @Override
    public Motorcycle convertToDbo(final MotorcycleDto motorcycleDto) {
        return modelMapper.map(motorcycleDto, Motorcycle.class);
    }

}
