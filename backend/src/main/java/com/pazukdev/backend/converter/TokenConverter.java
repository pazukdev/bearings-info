package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.VerificationTokenDto;
import com.pazukdev.backend.entity.VerificationToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class TokenConverter implements EntityDtoConverter<VerificationToken, VerificationTokenDto> {

    private final ModelMapper modelMapper;

    public TokenConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public VerificationTokenDto convertToDto(final VerificationToken token) {
        return modelMapper.map(token, VerificationTokenDto.class);
    }

    @Override
    public VerificationToken convertToEntity(final VerificationTokenDto dto) {
        return modelMapper.map(dto, VerificationToken.class);
    }

}
