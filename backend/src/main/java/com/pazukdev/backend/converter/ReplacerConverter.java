package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.item.ReplacerDto;
import com.pazukdev.backend.entity.item.Replacer;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
@Data
public class ReplacerConverter implements EntityDtoConverter<Replacer, ReplacerDto> {

    private final ModelMapper modelMapper;

    @Override
    public ReplacerDto convertToDto(final Replacer replacer) {
        final ReplacerDto dto = modelMapper.map(replacer, ReplacerDto.class);
        dto.setId(replacer.getId());
        return dto;
    }

    public ReplacerDto convertToDto(final Replacer replacer, final String itemNameToDisplay) {
        final ReplacerDto dto = convertToDto(replacer);
        dto.setButtonText(itemNameToDisplay);
        return dto;
    }

    @Override
    public Replacer convertToEntity(final ReplacerDto dto) {
        return modelMapper.map(dto, Replacer.class);
    }

}
