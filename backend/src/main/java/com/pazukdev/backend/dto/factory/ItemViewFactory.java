package com.pazukdev.backend.dto.factory;

import com.pazukdev.backend.dto.ImgViewData;
import com.pazukdev.backend.dto.NestedItemDto;
import com.pazukdev.backend.dto.view.ItemView;
import com.pazukdev.backend.entity.*;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.UserService;
import com.pazukdev.backend.util.*;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static com.pazukdev.backend.dto.factory.NestedItemDtoFactory.*;
import static com.pazukdev.backend.util.CategoryUtil.Category.VEHICLE;
import static com.pazukdev.backend.util.FileUtil.FileName;
import static com.pazukdev.backend.util.FileUtil.getTxtFileLines;
import static com.pazukdev.backend.util.ItemUtil.SpecialItemId.*;
import static com.pazukdev.backend.util.NestedItemUtil.createPossibleParts;
import static com.pazukdev.backend.util.NestedItemUtil.createReplacerDtos;
import static com.pazukdev.backend.util.SpecificStringUtil.*;
import static com.pazukdev.backend.util.TableUtil.createHeader;
import static com.pazukdev.backend.util.TableUtil.createReplacersTable;
import static com.pazukdev.backend.util.TranslatorUtil.translate;
import static com.pazukdev.backend.util.TranslatorUtil.translateNestedItemDtoList;

/**
 * @author Siarhei Sviarkaltsau
 */
@RequiredArgsConstructor
public class ItemViewFactory {

    private final ItemService itemService;
    private final Set<String> infoCategories;

    public ItemView createHomeView(final String userName, final String userLanguage) {
        return createItemView(MOTORCYCLE_CATALOGUE_VIEW.getItemId(), userName, userLanguage);
    }

    public ItemView createItemsManagementView(final String userName, final String userLanguage) {
        return createItemView(ITEMS_MANAGEMENT_VIEW.getItemId(), userName, userLanguage);
    }

    public ItemView createWishlistView(final String userName, final String userLanguage) {
        return createItemView(WISH_LIST_VIEW.getItemId(), userName, userLanguage);
    }

    public ItemView createUserListView(final String userName, final String userLanguage) {
        return createItemView(USER_LIST_VIEW.getItemId(), userName, userLanguage);
    }

    public ItemView createItemView(final Long itemId, final String userName, final String userLanguage) {
        final long businessLogicStartTime = System.nanoTime();

        final UserService userService = itemService.getUserService();
        final UserEntity currentUser = userService.findByName(userName);
        final WishList wishList = currentUser.getWishList();

        final ItemView basicView = new ItemView();
        basicView.setItemId(itemId);
        basicView.setWishListIds(UserUtil.collectWishListItemsIds(currentUser));
        basicView.setUserData(UserDtoFactory.createItemViewUserData(currentUser));

        ItemView view;

        if (itemId.equals(WISH_LIST_VIEW.getItemId())) {
            view = createWishListView(basicView, wishList, userService);
        } else if (itemId.equals(MOTORCYCLE_CATALOGUE_VIEW.getItemId())) {
            view = createVehicleView(basicView, userService);
        } else if (itemId.equals(ITEMS_MANAGEMENT_VIEW.getItemId())) {
            view = createItemsManagementView(basicView);
        } else if (itemId.equals(USER_LIST_VIEW.getItemId())) {
            view = createUsersListView(basicView, userService);
        } else {
            view = createOrdinaryItemView(basicView, itemId, currentUser, userService);
        }

        final double businessLogicEndTime = System.nanoTime();
        final double businessLogicDuration = businessLogicEndTime - businessLogicStartTime;

        if (!userLanguage.equals("en")) {
            try {
                translate("en", userLanguage, view, false);
            } catch (Exception e) {
                view.setErrorMessage(e.getMessage());
                return view;
            }
        }
        double translationDuration = System.nanoTime() - businessLogicEndTime;

        setTime(view, businessLogicDuration, translationDuration);
        return view;
    }

    public ItemView createNewItemView(final String category,
                                      final String name,
                                      final String userName,
                                      final String userLanguage) {
        final long businessLogicStartTime = System.nanoTime();

        final Item item = createNewItem(name, category, userName, userLanguage);
        final ItemView view = createItemView(item.getId(), userName, userLanguage);
        view.setNewItem(true);

        setTime (view, (double) (System.nanoTime() - businessLogicStartTime), null);
        return view;
    }

    private Item createNewItem(String name,
                               String category,
                               final String userName,
                               final String userLanguage) {
        final UserEntity creator = itemService.getUserService().findByName(userName);

        if (!userLanguage.equals("en")) {
            name = translate(userLanguage, "en", name, true);
            category = translate(userLanguage, "en", category, true);
        }

        final Item item = new Item();
        item.setName(name);
        item.setCategory(category);
        item.setImg("-");
        item.setCreatorId(creator.getId());
        item.setUserActionDate(DateUtil.now());
        item.setDescription(createEmptyDescription(category));
        itemService.update(item);
        UserActionUtil.processItemAction("create", item, creator, itemService);
        return item;
    }

    public ItemView updateItemView(final Long itemId,
                                   final String userName,
                                   final String userLanguage,
                                   final ItemView view) {
        final UserEntity user = itemService.getUserService().findByName(userName);
        final boolean removeItem = itemId.equals(ITEMS_MANAGEMENT_VIEW.getItemId());
        final boolean removeItemFromWishList = itemId.equals(WISH_LIST_VIEW.getItemId());
        final boolean removeUser = itemId.equals(USER_LIST_VIEW.getItemId());

        if (removeItem) {
            return removeItem(view, user, itemService.getUserService(), view.isHardDelete());
        }
        if (removeItemFromWishList) {
            return editWishList(view, user, userLanguage);
        }
        if (removeUser) {
            return removeUsers(view);
        }
        return updateItem(itemId, view, user, userLanguage);
    }

    private ItemView createOrdinaryItemView(final ItemView view,
                                            final Long itemId,
                                            final UserEntity currentUser,
                                            final UserService userService) {
        final Item item = itemService.getOne(itemId);
        final List<Item> allItems = itemService.findAll();
        final List<Item> sameCategoryItems = itemService.find(item.getCategory(), allItems);
        final String category = item.getCategory();
        final String name = item.getName();
        final ImgViewData imgViewData = ImgUtil.getImg(item);

        view.setSearchEnabled(true);
        view.setOrdinaryItem(true);
        view.setCategory(category);
        view.setLocalizedCategory(category);
        view.setName(name);
        view.setLocalizedName(name);
        view.setDefaultImg(imgViewData.getDefaultImg());
        view.setImg(imgViewData.getImg());
        view.setHeader(createHeader(item, itemService));
        view.setChildren(createChildren(item, userService, false));
        view.setAllChildren(createChildren(item, userService, true));
        view.setReplacersTable(createReplacersTable(item, userService));
        view.getPossibleParts().addAll(createPossibleParts(allItems, category, userService, infoCategories));
        view.getPossibleReplacers().addAll(createReplacerDtos(sameCategoryItems, itemId, userService));
        view.setCreatorId(item.getCreatorId());
        view.setCreatorName(UserUtil.getCreatorName(item, itemService.getUserService()));
        view.setLikeList(UserUtil.createLikeListDto(currentUser));
        LinkUtil.setLinksToItemView(view, item);
        view.setParents(createParentItemsView(item, userService));

        return view;
    }

    private static List<NestedItemDto> createChildren(final Item item,
                                                      final UserService userService,
                                                      final boolean all) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        addParts(item.getChildItems(), dtos, userService, all, null);
        return dtos;
    }

    public static void addParts(final Set<ChildItem> parts,
                                final List<NestedItemDto> dtos,
                                final UserService userService,
                                final boolean summary,
                                final Double parentQuantity) {
        for (final ChildItem part : parts) {
            boolean add = true;
            final NestedItemDto partDto = createChildItem(part, userService, !summary);
            Double quantity = null;
            if (summary) {
                quantity = getFirstNumber(part.getQuantity());
                if (quantity != null && parentQuantity != null) {
                    quantity = quantity * parentQuantity;
                    partDto.setSecondComment(doubleToString(quantity));
                }
                for (final NestedItemDto dto : dtos) {
                    final boolean itemIsInList = dto.getItemId().equals(partDto.getItemId());
                    if (itemIsInList) {
                        final String totalQuantity = sumQuantities(dto.getSecondComment(), doubleToString(quantity));
                        dto.setSecondComment(totalQuantity);
                        add = false;
                        break;
                    }
                }
            }
            if (add) {
                dtos.add(partDto);
            }
            if (summary) {
                addParts(part.getItem().getChildItems(), dtos, userService, true, quantity);
            }
        }
    }

    private ItemView createVehicleView(final ItemView view, final UserService userService) {
        final List<Item> vehicles = itemService.find(VEHICLE);

        final List<NestedItemDto> dtos = new ArrayList<>();
        vehicles.forEach(vehicle -> dtos.add(createVehicle(vehicle, userService)));
        view.setChildren(dtos);
        return view;
    }

    private ItemView createItemsManagementView(final ItemView view) {
        final List<Item> items = itemService.findAll();
        final Set<String> comments = getTxtFileLines(FileName.COMMENTS);

        final List<NestedItemDto> dtos = new ArrayList<>();
        items.forEach(item -> dtos.add(createItemForItemsManagement(item, itemService.getUserService(), comments)));
        view.setChildren(dtos);
        view.setAllCategories(new ArrayList<>(itemService.collectCategories(items)));
        return view;
    }

    private ItemView createParentItemsView(final Item item, final UserService userService) {
        final Set<String> comments = getTxtFileLines(FileName.COMMENTS);

        final ItemView view = new ItemView();
        final List<NestedItemDto> dtos = new ArrayList<>();
        itemService.findParents(item, itemService.findAll(), infoCategories)
                .forEach(parent -> dtos.add(createItemForItemsManagement(parent, userService, comments)));
        view.setChildren(dtos);
        return view;
    }

    private ItemView createUsersListView(final ItemView view, final UserService userService) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        userService.findAll().forEach(user -> dtos.add(createUser(user)));
        view.setChildren(dtos);
        return view;
    }

    private ItemView createWishListView(final ItemView view, final WishList wishList, final UserService userService) {
        final List<NestedItemDto> dtos = new ArrayList<>();
        wishList.getItems().forEach(item -> dtos.add(createWishListItem(item, userService)));
        view.setChildren(dtos);
        return view;
    }

    private ItemView updateItem(final Long itemId,
                                final ItemView view,
                                final UserEntity currentUser,
                                final String userLanguage) {

        final long businessLogicStartTime = System.nanoTime();

        final Item item = itemService.getOne(itemId);

        final long translationFromUserLang = System.nanoTime();
        if (!userLanguage.equals("en")) {
            try {
                translate(userLanguage, "en", view, true);
            } catch (Exception e) {
                view.setErrorMessage(e.getMessage());
                return view;
            }
        }
        final long translationFromUserLangDuration = System.nanoTime() - translationFromUserLang;

        final Map<String, String> headerMap = TableUtil.createHeaderMap(view.getHeader());

        ItemUtil.updateName(item, headerMap, itemService);
        ItemUtil.updateDescription(item, headerMap, itemService);
        ImgUtil.updateImg(view, item);
        ItemUtil.updateChildItems(item, view, itemService, currentUser);
        ItemUtil.updateReplacers(item, view, itemService, currentUser);
        LinkUtil.updateItemLinks(item, view);
        itemService.update(item);

        final ItemView newItemView = createItemView(itemId, currentUser.getName(), userLanguage);

        final double totalTranslationTime = view.getTranslationTime() * 1000000000 + translationFromUserLangDuration;
        setTime(newItemView, (double) (System.nanoTime() - businessLogicStartTime), totalTranslationTime);
        return newItemView;
    }

    private String createEmptyDescription(final String category) {
        final List<Item> items = itemService.find(category);
        if (items.isEmpty()) {
            return "";
        }
        final Map<String, String> descriptionMap = ItemUtil.toMap(items.get(0).getDescription());
        for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            entry.setValue("-");
        }
        return ItemUtil.toDescription(descriptionMap);
    }

    private ItemView editWishList(final ItemView view, final UserEntity user, final String userLanguage) {
        final Set<ChildItem> oldItems = new HashSet<>(user.getWishList().getItems());

        Set<ChildItem> newItems = ChildItemUtil.createPartsFromItemView (view, itemService);
        view.getHeader().getRows().get(0).setValue(String.valueOf(newItems.size()));

        // delete all items from wishlist and child item repository and save that state
        user.getWishList().getItems().clear();
        itemService.getUserService().update(user);
        for (final ChildItem oldItem : oldItems) {
            if (!newItems.contains(oldItem)) {
                final Long id = oldItem.getId();
                itemService.getChildItemRepository().deleteById(id);
            }
        }

        // add all actual items to wishlist after translation and save that state
        final List<NestedItemDto> dtos = view.getChildren();
        translateNestedItemDtoList(userLanguage, "en", dtos);
        newItems = ChildItemUtil.createPartsFromItemView (view, itemService);
        user.getWishList().getItems().addAll(newItems);
        itemService.getUserService().update(user);

        view.setWishListIds(ChildItemUtil.collectIds(newItems));
        translateNestedItemDtoList("en", userLanguage, dtos);
        return view;
    }

    private ItemView removeUsers(final ItemView view) {
        for (final Long userToRemoveId : view.getIdsToRemove()) {
            itemService.getUserService().delete(userToRemoveId);
        }
        view.getIdsToRemove().clear();
        return view;
    }

    private ItemView removeItem(final ItemView view,
                                final UserEntity user,
                                final UserService userService,
                                final boolean hardDelete) {
        removeItems (view.getIdsToRemove(), user, userService, hardDelete);
        view.getIdsToRemove().clear();
        return view;
    }

    private void removeItems(final Set<Long> idsToRemove,
                             final UserEntity currentUser,
                             final UserService userService,
                             final boolean hardDelete) {
        for (final Long idToRemove : idsToRemove) {
            final Item itemToRemove = itemService.getOne(idToRemove);
            removeItemFromAllWishLists(itemToRemove, userService);
            removeItemFromAllParentItems(idToRemove, currentUser, hardDelete);
            removeItem(itemToRemove, currentUser, hardDelete);
        }
    }

    private void removeItem(final Item itemToRemove, final UserEntity user, final boolean hardDelete) {
        if (hardDelete) {
            final Long id = itemToRemove.getId();
            itemService.delete(id);
//            itemService.delete(id);
//            Set<Replacer> replacersToRemove = new HashSet<>();
//            for (final Replacer replacer : itemService.getReplacerRepository().findAll()) {
//                if (replacer.getItem().getId().equals(id)) {
//                    replacersToRemove.add(replacer);
//                }
//            }
//            for (Replacer replacer : replacersToRemove) {
//                itemService.getReplacerRepository().deleteById(replacer.getId());
//            }
            return;
        }
        itemToRemove.setStatus("deleted");
        itemToRemove.setUserActionDate(DateUtil.now());
        itemService.update(itemToRemove);
        UserActionUtil.processItemAction("delete", itemToRemove, user, itemService);
    }

    private void removeItemFromAllWishLists(final Item itemToRemove, final UserService userService) {
        for (final UserEntity user : userService.findAll()) {
            final List<ChildItem> wishListItemsCopy = new ArrayList<>(user.getWishList().getItems());
            for (final ChildItem wishListItem : wishListItemsCopy) {
                if (wishListItem.getItem().getId().equals(itemToRemove.getId())) {
                    user.getWishList().getItems().remove(wishListItem);
                }
            }
            userService.update(user);
        }
    }

    private void removeItemFromAllParentItems(final Long idToRemove, final UserEntity user, boolean hardDelete) {
        final String actionType = "delete";

        for (final Item item : itemService.findAll()) {
            for (final Replacer replacer : item.getReplacers()) {
                final Item nestedItem = replacer.getItem();
                if (nestedItem.getId().equals(idToRemove)) {
                    replacer.setStatus("deleted");
                    itemService.getReplacerRepository().save(replacer);
                    UserActionUtil.processReplacerAction(actionType, replacer, item, user, itemService);
                }
            }

            for (final ChildItem part : item.getChildItems()) {
                final Item nestedItem = part.getItem();
                if (nestedItem.getId().equals(idToRemove)) {
                    part.setStatus("deleted");
                    itemService.getChildItemRepository().save(part);
                    UserActionUtil.processPartAction(actionType, part, item, user, itemService);
                }
            }

            if (hardDelete) {
                hardDeleteItemsInSet(item.getReplacers());
                hardDeleteItemsInSet(item.getChildItems());
            }

            itemService.update(item);
        }
    }

    private <E extends AbstractEntity> void hardDeleteItemsInSet(final Set<E> set) {
        set.removeIf(abstractEntity -> abstractEntity.getStatus().equals("deleted"));
    }

    private void setTime(final ItemView view,
                         final Double businessLogicDuration,
                         final Double translationDuration) {
        final double secondsInNano = 0.000000001;
        if (businessLogicDuration != null) {
            view.setBusinessLogicTime(businessLogicDuration * secondsInNano);
        }
        if (translationDuration != null) {
            view.setTranslationTime(translationDuration * secondsInNano);
        }
        view.setResponseTotalTime(view.getBusinessLogicTime() + view.getTranslationTime());
    }

}
