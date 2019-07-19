package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class UserConverter implements EntityDtoConverter<User, UserDto> {

    private final ModelMapper modelMapper;

    public UserConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto convertToDto(final User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User convertToEntity(final UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

}
