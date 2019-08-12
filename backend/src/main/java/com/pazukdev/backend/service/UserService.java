package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.UserRepository;
import com.pazukdev.backend.validator.CredentialsValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class UserService extends AbstractService<UserEntity, UserDto> {

    private final PasswordEncoder passwordEncoder;
    private final CredentialsValidator credentialsValidator;

    public UserService(final UserRepository repository,
                       final UserConverter converter,
                       final PasswordEncoder passwordEncoder,
                       final CredentialsValidator credentialsValidator) {
        super(repository, converter);
        this.passwordEncoder = passwordEncoder;
        this.credentialsValidator = credentialsValidator;
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
        final boolean userExists = findByName(dto.getName()) != null;
        return credentialsValidator.validate(dto, userExists);
    }

}
