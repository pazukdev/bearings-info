package com.pazukdev.backend.service;

import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.dto.item.TransitiveItemDto;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.WishList;
import com.pazukdev.backend.entity.item.TransitiveItem;
import com.pazukdev.backend.repository.UserRepository;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.validator.CredentialsValidator;
import org.apache.commons.lang3.StringUtils;
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
    private final TransitiveItemService transitiveItemService;
    private final WishListService wishListService;

    public UserService(final UserRepository repository,
                       final UserConverter converter,
                       final PasswordEncoder passwordEncoder,
                       final CredentialsValidator credentialsValidator,
                       TransitiveItemService transitiveItemService, final WishListService wishListService) {
        super(repository, converter);
        this.passwordEncoder = passwordEncoder;
        this.credentialsValidator = credentialsValidator;
        this.transitiveItemService = transitiveItemService;
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

    public TableViewDto createTableView(final String userName) {
        return createTableView(getAllItems(userName));
    }

    public TableViewDto createTableView(final List<TransitiveItem> items) {
        final List<TableDto> tables = new ArrayList<>();
        for (final List<TransitiveItem> categorizedItems : ItemUtil.categorize(items)) {
            tables.add(createTable(categorizedItems));
        }
        return new TableViewDto(items.size(), tables);
    }

    public UserEntity getAdmin() {
        return getOne(1L);
    }

    public TableDto createTable(final String userName) {
        return createTable(getAllItems(userName));
    }

    public TableDto createTable(final List<TransitiveItem> items) {
        final String tableName = items.get(0).getCategory();
        final List<String[]> rows = new ArrayList<>();
        for (final TransitiveItem item : items) {
            final String[] row = {
                    item.getCategory(),
                    item.getName(),
                    item.getName().toString(),
                    item.getId().toString()};
            rows.add(row);
        }
        final String[][] rowArray = rows.toArray(new String[0][]);
        return new TableDto(tableName, rowArray);
    }

    public List<TransitiveItem> getAllItems(final String userName) {
//        final List<TransitiveItem> items = new ArrayList<>(getWishList(userName).getItems());
//        ItemUtil.sort(items);
//        return items;
        return null;
    }

    private WishList getWishList(final String userName) {
        return findByName(userName).getWishList();
    }

    public Boolean addItem(final String userName, final TransitiveItemDto transitiveItemDto) {
//        final WishList wishList = getWishList(userName);
//        final TransitiveItem item = transitiveItemService.create(transitiveItemDto);
//        wishList.getItems().add(item);
//        wishListService.update(wishList);
//        return true;
        return false;
    }

    public Boolean removeItem(final String userName, final Long itemId) {
//        final WishList wishList = getWishList(userName);
//        for (final TransitiveItem item : wishList.getItems()) {
//            if (item.getId().longValue() == itemId) {
//                wishList.getItems().remove(item);
//                wishListService.update(wishList);
//                transitiveItemService.delete(itemId);
//                return true;
//            }
//        }
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
                final WishList wishList = wishListService.create(new WishListDto());
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
