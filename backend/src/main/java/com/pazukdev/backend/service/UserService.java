package com.pazukdev.backend.service;

import com.pazukdev.backend.constant.Status;
import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserItemItemReport;
import com.pazukdev.backend.dto.UserItemReport;
import com.pazukdev.backend.dto.user.UserDto;
import com.pazukdev.backend.dto.view.UserView;
import com.pazukdev.backend.entity.ChildItem;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.WishList;
import com.pazukdev.backend.repository.ChildItemRepository;
import com.pazukdev.backend.repository.UserRepository;
import com.pazukdev.backend.repository.WishListRepository;
import com.pazukdev.backend.util.CollectionUtil;
import com.pazukdev.backend.util.FileUtil;
import com.pazukdev.backend.util.ImgUtil;
import com.pazukdev.backend.validator.UserDataValidator;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.pazukdev.backend.util.CSVUtil.getValue;
import static com.pazukdev.backend.util.ChildItemUtil.collectIds;
import static com.pazukdev.backend.util.ChildItemUtil.createNameForWishListItem;
import static com.pazukdev.backend.util.SpecificStringUtil.*;
import static com.pazukdev.backend.util.UserUtil.UserParam;
import static com.pazukdev.backend.util.UserUtil.isAdmin;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
@Getter
public class UserService extends AbstractService<UserEntity, UserDto> {

    private static final String ADMIN_NAME = "admin";
    private static final Long ADMIN_ID = 2L;

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

    public UserRepository getRepository() {
        return (UserRepository) repository;
    }

    public void save(final List<UserEntity> users) {
        for (final UserEntity user : users) {
            repository.save(user);
        }
    }

    @Transactional
    public UserEntity findActiveByName(final String name) {
        return ((UserRepository) repository).findFirstByNameAndStatus(name, Status.ACTIVE);
    }

    @Transactional
    @Override
    public UserEntity findFirstByName(final String name) {
        return ((UserRepository) repository).findFirstByName(name);
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
        boolean admin = isAdmin(findActiveByName(view.getCurrentUserName()));

        final String newName = view.getName();
        final String newEmail = view.getEmail();

        final UserEntity user = findOne(view.getId());
        final boolean changeName = newName!= null && !user.getName().equals(newName);
        final boolean changeEmail = newEmail != null && !StringUtils.equalsIgnoreCase(user.getEmail(), newEmail);
        final boolean changePassword = admin ? view.getNewPassword() != null : view.getOldPassword() != null;

        final List<String> validationMessages = new ArrayList<>();
        if (changeName) {
            validationMessages.addAll(userDataValidator.validateName(newName, this));
        }
        if (changeEmail) {
            validationMessages.addAll(userDataValidator.validateEmail(newEmail, this));
        }
        if (changePassword) {
            validationMessages.addAll(userDataValidator.validateChangedPassword(view, passwordEncoder, admin, this));
        }

        if (validationMessages.isEmpty()) {
            if (changeName) {
                user.setName(newName);
            }
            if (changeEmail) {
                user.setEmail(newEmail);
            }
            if (changePassword) {
                user.setPassword(passwordEncoder.encode(view.getNewPassword()));
            }
            user.setRole(Role.valueOf(view.getRole().toUpperCase()));
            user.setCountry(view.getCountry());
            ImgUtil.updateImg(view, user);
            user.setStatus(view.getStatus());

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
        final UserEntity currentUser = findFirstByName(userName);

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
        return findOne(ADMIN_ID);
    }

    public UserEntity findAdmin(final List<UserEntity> users) {
        return CollectionUtil.findFirstByName(ADMIN_NAME, users);
    }

    @Transactional
    public List<UserEntity> getUsersFromRecoveryFile(final boolean recoverUserData) {
        final List<UserEntity> users = new ArrayList<>();
        final List<List<String>> rows = FileUtil.readGoogleDocSpreadsheet(FileUtil.FileId.USER);
        final List<String> header = rows.get(0);
        rows.remove(0);
        for (final List<String> userData : rows) {
            final Long id = Long.valueOf(userData.get(header.indexOf("id")));
            UserEntity user = repository.findById(id).orElse(null);
            if (user != null && !recoverUserData) {
                continue;
            } else if (user == null) {
                user = new UserEntity();
                user.setWishList(new WishList());
            }
            user.setId(Long.valueOf(getValue("id", header, userData)));
            user.setRole(Role.valueOf(getValue("role", header, userData)));
            user.setName(getValue("name", header, userData));
            user.setRating(Integer.valueOf(getValue("rating", header, userData)));
            user.setStatus(getValue("status", header, userData));
            user.setEmail(getValue("email", header, userData));
            user.setPassword(getValue("password", header, userData));
            user.setCountry(getValue("country", header, userData));
            user.setImg(getValue("img", header, userData));

            repository.save(user);
            users.add(user);
        }
        return users;
    }

    @Transactional
    public void recoverUserActions(final List<UserEntity> users, final ItemService itemService) {
        final List<List<String>> rows = FileUtil.readGoogleDocSpreadsheet(FileUtil.FileId.USER);
        final List<String> header = rows.get(0);
        rows.remove(0);

        final Map<Long, List<ChildItem>> wishlistItems = new HashMap<>();
        final Map<Long, UserItemReport<Item>> reports = new HashMap<>();

        for (final List<String> userData : rows) {
            final Long id = Long.valueOf(userData.get(header.indexOf(UserParam.ID)));
            wishlistItems.put(id, getWishlistItems(getValue(UserParam.WISHLIST_ITEMS, header, userData), itemService));
            reports.put(id, UserItemItemReport.create(header, userData, itemService));
        }

        for (final UserEntity user : users) {
            user.getWishList().getItems().clear();
            user.getWishList().getItems().addAll(wishlistItems.get(user.getId()));

            final UserItemReport<Item> report = reports.get(user.getId());

            for (final Item item : report.getCreatedItems()) {
                item.setCreatorId(user.getId());
                itemService.getRepository().save(item);
            }

            for (final Item item : report.getLikedItems()) {
                item.getLikedUsers().add(user);
                itemService.getRepository().save(item);
            }

            for (final Item item : report.getDislikedItems()) {
                item.getDislikedUsers().add(user);
                itemService.getRepository().save(item);
            }

        }
    }

    private List<ChildItem> getWishlistItems(final String source, final ItemService itemService) {
        final List<ChildItem> items = new ArrayList<>();
        if (isEmpty(source)) {
            return items;
        }
        for (String s : source.split(";")) {
            s = s.trim();
            final String name = getStringBeforeParentheses(s);
            final String additionalData = getStringBetweenParentheses(s);
            final String category = additionalData.split("!")[0].trim();
            final String location = additionalData.split("!")[1].trim();
            final String quantity = additionalData.split("!")[2].trim();

            final Item item = itemService.findFirstByCategoryAndName(category, name);
            if (item == null) {
                continue;
            }

            final ChildItem wishlistItem = new ChildItem();
            wishlistItem.setName("Wishlist - " + item.getName());
            wishlistItem.setItem(item);
            wishlistItem.setLocation(location);
            wishlistItem.setQuantity(quantity);

            items.add(wishlistItem);

        }
        return items;
    }

}
