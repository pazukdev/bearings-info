package com.pazukdev.backend.service;

import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.WishListEntity;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.repository.UserRepository;
import com.pazukdev.backend.validator.CredentialsValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public TableDto getItemTable(final String userName) {
        final Set<ItemEntity> items = getAllItems(userName);
        final List<String[]> rows = new ArrayList<>();
        for (final ItemEntity item : items) {
            final String[] row = {item.getType(), item.getName(), item.getQuantity().toString()};
            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(rowArray);
    }

    public Set<ItemEntity> getAllItems(final String userName) {
        return getWishList(userName).getItems();
    }

    private WishListEntity getWishList(final String userName) {
        return findByName(userName).getWishList();
    }

    public Boolean addItem(final String userName, final ItemDto itemDto) {
        final WishListEntity wishList = getWishList(userName);
        final ItemEntity item = itemService.create(itemDto);
        wishList.getItems().add(item);
        wishListService.update(wishList);
        return true;
    }

    public Boolean removeItem(final String userName, final Long itemId) {
        final WishListEntity wishList = getWishList(userName);
        for (final ItemEntity item : wishList.getItems()) {
            if (item.getId().longValue() == itemId) {
                wishList.getItems().remove(item);
                wishListService.update(wishList);
                itemService.delete(itemId);
                return true;
            }
        }
        return false;
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
