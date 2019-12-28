package com.pazukdev.backend.service;

import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.user.UserDto;
import com.pazukdev.backend.entity.ChildItem;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.UserRepository;
import com.pazukdev.backend.util.ChildItemUtil;
import com.pazukdev.backend.validator.CredentialsValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public UserEntity findByEmail(final String email) {
        return ((UserRepository) repository).findByEmail(email);
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

    @Transactional
    public boolean addItemToWishList(final Item item, final String userName) {
        final UserEntity currentUser = findByName(userName);

        final Set<Long> ids = ChildItemUtil.collectIds(currentUser.getWishList().getItems());

        if (!ids.contains(item.getId())) {
            final ChildItem childItem = new ChildItem();
            childItem.setName(ChildItemUtil.createNameForWishListItem(item.getName()));
            childItem.setItem(item);
            currentUser.getWishList().getItems().add(childItem);
            update(currentUser);
            return true;
        } else {
            return false;
        }

    }

    public UserEntity getAdmin() {
        return getOne(2L);
    }

    private List<String> createOrUpdateWithCredentialsValidation(final Long id,
                                                                 final UserDto dto,
                                                                 final boolean create) {
        final List<String> validationMessages = validateCredentials(dto, create);
        if (validationMessages.isEmpty()) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            if (create) {
                final UserEntity user = new UserEntity();
                user.setPassword(dto.getPassword());
                user.setEmail(dto.getEmail());
                user.setName(dto.getName());
                repository.save(user);
            } else {
                update(id, dto);
            }
        }
        return validationMessages;
    }

    private List<String> validateCredentials(final UserDto dto, final boolean checkIfAlreadyExists) {
        boolean nameExists = false;
        boolean emailExists = false;
        if (checkIfAlreadyExists) {
            nameExists = findByName(dto.getName()) != null;
            emailExists = findByEmail(dto.getEmail()) != null;
        }
        return credentialsValidator.validate(dto, nameExists, emailExists);
    }

}
