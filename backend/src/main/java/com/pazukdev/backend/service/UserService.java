package com.pazukdev.backend.service;

import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.UserRepository;
import com.pazukdev.backend.validator.CredentialsValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    @Override
    public UserEntity findByName(final String name) {
        return ((UserRepository) repository).findByName(name);
    }

    @Transactional
    @Override
    public List<UserEntity> findAll() {
        final List<UserEntity> users = super.findAll();
        users.sort(Comparator.comparing(UserEntity::getRole));
        return users;
    }

    @Transactional
    public List<String> createUser(final UserDto dto) {
        final Long id = null;
        final boolean create = true;
        return createOrUpdateWithCredentialsValidation(id, dto, create);
    }

    @Transactional
    public List<String> updateUser(final Long id, final UserDto dto) {
        final boolean create = false;
        return createOrUpdateWithCredentialsValidation(id, dto, create);
    }

    @Transactional
    public Set<String> getRoles() {
        return new HashSet<>(Arrays.asList(Role.USER.name(), Role.ADMIN.name()));
    }

    private List<String> createOrUpdateWithCredentialsValidation(final Long id,
                                                                 final UserDto dto,
                                                                 final boolean create) {
        final List<String> validationMessages = validateCredentials(dto, create);
        if (validationMessages.isEmpty()) {
            if (StringUtils.isBlank(dto.getRole())) {
                dto.setRole(Role.USER.name());
            }
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            if (create) {
                create(dto);
            } else {
                update(id, dto);
            }
        }
        return validationMessages;
    }

    private List<String> validateCredentials(final UserDto dto, final boolean checkIfAlreadyExists) {
        boolean userExists = false;
        if (checkIfAlreadyExists) {
            userExists = findByName(dto.getName()) != null;
        }
        return credentialsValidator.validate(dto, userExists);
    }

}
