package com.pazukdev.backend.service;

import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.user.UserDto;
import com.pazukdev.backend.dto.view.UserView;
import com.pazukdev.backend.entity.ChildItem;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.ChildItemRepository;
import com.pazukdev.backend.repository.UserRepository;
import com.pazukdev.backend.repository.WishListRepository;
import com.pazukdev.backend.util.ImgUtil;
import com.pazukdev.backend.validator.UserDataValidator;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.pazukdev.backend.util.ChildItemUtil.collectIds;
import static com.pazukdev.backend.util.ChildItemUtil.createNameForWishListItem;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
@Getter
public class UserService extends AbstractService<UserEntity, UserDto> {

    private final PasswordEncoder passwordEncoder;
    private final UserDataValidator userDataValidator;
    private final WishListRepository wishListRepository;
    private final ChildItemRepository childItemRepository;

    public UserService(final UserRepository repository,
                       final UserConverter converter,
                       final PasswordEncoder passwordEncoder,
                       final UserDataValidator userDataValidator,
                       final WishListRepository wishListRepository,
                       final ChildItemRepository childItemRepository) {
        super(repository, converter);
        this.passwordEncoder = passwordEncoder;
        this.userDataValidator = userDataValidator;
        this.wishListRepository = wishListRepository;
        this.childItemRepository = childItemRepository;
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
        final List<String> validationMessages = userDataValidator.validateSignUpData(dto, this);
        if (validationMessages.isEmpty()) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            final UserEntity user = new UserEntity();
            user.setPassword(dto.getPassword());
            user.setEmail(dto.getEmail());
            user.setName(dto.getName());
            repository.save(user);
        }
        return validationMessages;
    }

    @Transactional
    public List<String> updateUser(final UserView view) {
        final String newName = view.getName();
        final String newEmail = view.getEmail();

        final UserEntity user = getOne(view.getId());
        boolean changeName = newName!= null && !user.getName().equals(newName);
        boolean changeEmail = newEmail != null && !StringUtils.equalsIgnoreCase(user.getEmail(), newEmail);
        boolean changePassword = view.getOldPassword() != null;

        final List<String> validationMessages = new ArrayList<>();
        if (changeName) {
            validationMessages.addAll(userDataValidator.validateName(newName, this));
        }
        if (changeEmail) {
            validationMessages.addAll(userDataValidator.validateEmail(newEmail, this));
        }
        if (changePassword) {
            validationMessages.addAll(userDataValidator.validateChangedPassword(view, passwordEncoder, this));
        }

        if (validationMessages.isEmpty()) {
            if (changeName) {
                user.setName(newName);
            }
            if (changeEmail) {
                user.setEmail(newEmail);
            }
            user.setRole(Role.valueOf(view.getRole().toUpperCase()));
            user.setCountry(view.getCountry());
            ImgUtil.updateImg(view, user);
            if (changePassword) {
                user.setPassword(passwordEncoder.encode(view.getNewPassword()));
            }
            repository.save(user);
        }

        return validationMessages;
    }

    @Transactional
    public Set<String> getRoles() {
        return new HashSet<>(Arrays.asList(Role.USER.name(), Role.ADMIN.name()));
    }

    @Transactional
    public boolean addItemToWishList(final Item item, final String userName) {
        final UserEntity currentUser = findByName(userName);

        final Set<Long> ids = collectIds(currentUser.getWishList().getItems());

        if (!ids.contains(item.getId())) {
            final ChildItem childItem = new ChildItem();
            childItem.setName(createNameForWishListItem(item.getName()));
            childItem.setItem(item);
            currentUser.getWishList().getItems().add(childItem);
            update(currentUser);
            return true;
        }

        return  false;
    }

    public UserEntity getAdmin() {
        return getOne(2L);
    }

}
