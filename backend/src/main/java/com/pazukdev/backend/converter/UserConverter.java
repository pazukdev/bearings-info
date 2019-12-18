package com.pazukdev.backend.converter;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.user.UserDto;
import com.pazukdev.backend.dto.user.UserView;
import com.pazukdev.backend.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class UserConverter implements EntityDtoConverter<UserEntity, UserDto> {

    private final ModelMapper modelMapper;

    public UserConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto convertToDto(final UserEntity user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserEntity convertToEntity(final UserDto dto) {
        return modelMapper.map(dto, UserEntity.class);
    }

    public UserView convertToUserView(final UserEntity user) {
        final UserView userView = new UserView();
        userView.setName(user.getName());
        userView.setRole(user.getRole().toString().toLowerCase());
        userView.setRating(user.getRating().toString());
        userView.setEmail(user.getEmail());
        return userView;
    }

}
