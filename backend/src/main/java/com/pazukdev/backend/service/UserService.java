package com.pazukdev.backend.service;

import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.WishListEntity;
import com.pazukdev.backend.entity.item.ItemEntity;
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
    private final ItemService itemService;
    private final WishListService wishListService;

    public UserService(final UserRepository repository,
                       final UserConverter converter,
                       final PasswordEncoder passwordEncoder,
                       final CredentialsValidator credentialsValidator,
                       ItemService itemService, final WishListService wishListService) {
        super(repository, converter);
        this.passwordEncoder = passwordEncoder;
        this.credentialsValidator = credentialsValidator;
        this.itemService = itemService;
        this.wishListService = wishListService;
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

    public ItemEntity addItem(final String userName, final ItemDto itemDto) {
        final ItemEntity item = itemService.create(itemDto);
        final UserEntity user = findByName(userName);
        final WishListEntity wishList = user.getWishList();
        wishList.getItems().add(item);
        wishListService.update(wishList);
        update(user);
        return item;
    }

//        public Boolean removeItem(final Long wishListId, final Long bearingToRemoveId) {
//        final Set<BearingEntity> bearings = repository.getOne(wishListId).getBearings();
//        for (final BearingEntity bearing : bearings) {
//            if (bearing.getId().longValue() == bearingToRemoveId.longValue()) {
//                return bearings.remove(bearing);
//            }
//        }
//        return false;
//    }

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
                final WishListEntity wishList = wishListService.create(new WishListDto());
                dto.setWishListId(wishList.getId());
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
