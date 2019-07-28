package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class UserService extends AbstractService<UserEntity, UserDto> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserConverter converter) {
        super(repository, converter);
    }

    @Override
    public UserEntity findByName(final String name) {
        return ((UserRepository) repository).findByName(name);
    }

    public void save(UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        super.create(dto);
    }

}
