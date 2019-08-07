package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class UserService extends AbstractService<UserEntity, UserDto> {

    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository repository,
                       final UserConverter converter,
                       final PasswordEncoder passwordEncoder) {
        super(repository, converter);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity findByName(final String name) {
        return ((UserRepository) repository).findByName(name);
    }

    public List<String> createUser(final UserDto dto) {
        dto.setRole("USER");
        return createWithCredentialsValidation(dto);
    }

    public List<String> createAdmin(final UserDto dto) {
        dto.setRole("ADMIN");
        return createWithCredentialsValidation(dto);
    }

    private List<String> createWithCredentialsValidation(final UserDto dto) {
        final List<String> validationMessages = validateCredentials(dto);
        if (validationMessages.isEmpty()) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            create(dto);
        }
        return validationMessages;
    }

    private List<String> validateCredentials(final UserDto dto) {
        final List<String> validationMessages = new ArrayList<>();
        if (StringUtils.isBlank(dto.getName())) {
            validationMessages.add("Login is empty");
        }
        if (StringUtils.isBlank(dto.getPassword())) {
            validationMessages.add("Password is empty");
        }
        if (!dto.getRepeatedPassword().equals(dto.getPassword())) {
            validationMessages.add("Passwords are different");
        }
        if (findByName(dto.getName()) != null) {
            validationMessages.add("User with this Login already exists");
        }
        return validationMessages;
    }

}
