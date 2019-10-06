package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.converter.ReplacerConverter;
import com.pazukdev.backend.dto.item.NestedItemDto;
import com.pazukdev.backend.dto.item.TransitiveItemDescriptionMap;
import com.pazukdev.backend.dto.item.TransitiveItemDto;
import com.pazukdev.backend.dto.table.ItemView;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.WishList;
import com.pazukdev.backend.entity.item.*;
import com.pazukdev.backend.repository.ChildItemRepository;
import com.pazukdev.backend.repository.ItemRepository;
import com.pazukdev.backend.repository.ReplacerRepository;
import com.pazukdev.backend.repository.UserActionRepository;
import com.pazukdev.backend.util.*;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.pazukdev.backend.util.ItemUtil.createDescriptionMap;
import static com.pazukdev.backend.util.NestedItemUtil.prepareNestedItemDtosToConverting;
import static com.pazukdev.backend.util.SpecificStringUtil.replaceBlankWithDash;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ItemService extends AbstractService<Item, TransitiveItemDto> {

    final TransitiveItemService transitiveItemService;
    @Getter
    private final ItemRepository itemRepository;
    private final ChildItemRepository partRepository;
    private final UserService userService;
    private final UserActionRepository userActionRepository;
    private final ReplacerRepository replacerRepository;
    private final ReplacerConverter replacerConverter;


    public ItemService(final ItemRepository itemRepository,
                       final ItemConverter converter,
                       final TransitiveItemService transitiveItemService,
                       final ChildItemRepository partRepository,
                       final UserService userService,
                       final UserActionRepository userActionRepository,
                       final ReplacerRepository replacerRepository,
                       final ReplacerConverter replacerConverter) {
        super(itemRepository, converter);
        this.transitiveItemService = transitiveItemService;
        this.itemRepository = itemRepository;
        this.partRepository = partRepository;
        this.userService = userService;
        this.userActionRepository = userActionRepository;
        this.replacerRepository = replacerRepository;
        this.replacerConverter = replacerConverter;
    }

    @Getter
    private enum SpecialItemId {

        WISH_LIST_VIEW(-3),
        MOTORCYCLE_CATALOGUE_VIEW(-2),
        ITEMS_MANAGEMENT_VIEW(-1);

        private final int itemId;

        SpecialItemId(final int itemId) {
            this.itemId = itemId;
        }
    }

    @Transactional
    @Override
    public Item findByName(String name) {
        return ((ItemRepository) repository).findByName(name);
    }

    @Transactional
    public Item find(final String category, final String name) {
        for (final Item item : find(category)) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Transactional
    public List<Item> find(final String category) {
        return find(category, findAll());
    }

    @Transactional
    public List<Item> find(final String category, final List<Item> items) {
        final List<Item> categorizedItems = new ArrayList<>();
        for (final Item item : items) {
            if (item.getCategory().toLowerCase().equals(category.toLowerCase())) {
                categorizedItems.add(item);
            }
        }
        return categorizedItems;
    }

    @Transactional
    public Item saveAsItem(final TransitiveItem transitiveItem) {
        final Item item = getOrCreate(transitiveItem);
        item.setName(replaceBlankWithDash(item.getName()));
        return repository.save(item);
    }

    @Transactional
    public ItemView createItemView(final Long itemId, final String userName) {
        final UserEntity currentUser = userService.findByName(userName);
        final WishList wishList = currentUser.getWishList();

        final ItemView itemView = new ItemView();
        itemView.setItemId(itemId);
        itemView.setWishListIds(UserUtil.collectWishListItemsIds(currentUser));

        if (itemId == SpecialItemId.WISH_LIST_VIEW.getItemId()) {
            return ItemViewUtil.createWishListView(itemView, wishList, this, userService);
        }

        if (itemId == SpecialItemId.MOTORCYCLE_CATALOGUE_VIEW.getItemId()) {
            return createMotorcycleCatalogueView(itemView);
        }

        if (itemId == SpecialItemId.ITEMS_MANAGEMENT_VIEW.getItemId()) {
            return ItemViewUtil.createItemsManagementView(itemView, this, userService);
        }

        return ItemViewUtil.createOrdinaryItemView(itemView, itemId, this, replacerConverter, userService);
    }

    @Transactional
    public ItemView createNewItemView(final String category,
                                      final String name,
                                      final String userName) {

        final UserEntity creator = userService.findByName(userName);

        final Item item = new Item();
        item.setName(name);
        item.setCategory(category);
        item.setCreatorId(creator.getId());
        item.setUserActionDate(DateUtil.now());
        item.setDescription(createEmptyDescription(category));
        itemRepository.save(item);
        final ItemView itemView = createItemView(item.getId(), userName);
        itemView.setNewItem(true);

        userActionRepository.save(UserActionUtil.create(creator, "create", "item", item));

        return itemView;
    }

    private String createEmptyDescription(final String category) {
        final List<Item> items = find(category);
        if (items.isEmpty()) {
            return "";
        }
        final Map<String, String> descriptionMap = ItemUtil.toMap(items.get(0).getDescription());
        for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
            entry.setValue("-");
        }
        return ItemUtil.toDescription(descriptionMap);
    }

    @Transactional
    public ItemView update(final Long itemId, final String userName, final ItemView itemView) {
        final UserEntity user = userService.findByName(userName);
        final boolean removeItem = itemId == SpecialItemId.ITEMS_MANAGEMENT_VIEW.getItemId();
        final boolean removeItemFromWishList = itemId == SpecialItemId.WISH_LIST_VIEW.getItemId();

        if (removeItem) {
            return removeItem(itemView, user);
        }
        if (removeItemFromWishList) {
            return removeItemFromWishList(itemView, user);
        }
        return updateItem(itemId, itemView, user);
    }

    private ItemView removeItem(final ItemView itemView, final UserEntity user) {
        removeItems(itemView.getIdsToRemove(), user);
        itemView.getIdsToRemove().clear();
        return itemView;
    }

    private ItemView removeItemFromWishList(final ItemView itemView, final UserEntity user) {
        final String actionType = "remove from wishlist";
        final String itemType = "wishlist item";

        for (final Long itemId : itemView.getIdsToRemove()) {
            final Item item = getOne(itemId);
            user.getWishList().getItems().remove(item);

            final UserAction userAction = UserActionUtil.create(user, actionType, itemType, item);
            userActionRepository.save(userAction);
        }
        userService.update(user);
        itemView.getIdsToRemove().clear();
        return itemView;
    }

    private ItemView updateItem(final Long itemId, final ItemView itemView, final UserEntity user) {
        final Item item = getOne(itemId);
        final Map<String, String> headerMatrixMap = createHeaderMatrixMap(itemView);
        updateName(item, headerMatrixMap);
        updateDescription(item, headerMatrixMap);
        updateParts(item, itemView);
        updateReplacers(item, itemView);
        updateWishList(item, itemView, user);

        userActionRepository.save(UserActionUtil.create(user, "update", "item", item));
        itemRepository.save(item);
        return createItemView(itemId, user.getName());
    }

    private void updateWishList(final Item item, final ItemView itemView, final UserEntity currentUser) {
        if (itemView.isAddToWishList()) {
            addItemToWishList(item.getId(), currentUser);
            itemView.setAddToWishList(false);
        }
    }

    private void addItemToWishList(final Long itemId, final UserEntity currentUser) {
        currentUser.getWishList().getItems().add(getOne(itemId));
        userService.update(currentUser);
    }

    private void removeItems(final Set<Long> idsToRemove, final UserEntity user) {
        final String actionType = "delete";
        for (final Long idToRemove : idsToRemove) {
            removeItemFromAllParentItems(idToRemove, user, actionType);
            removeItem(getOne(idToRemove), user, actionType);
        }
    }

    private void removeItem(final Item itemToRemove, final UserEntity user, final String actionType) {
        itemToRemove.setStatus("deleted");
        itemToRemove.setUserActionDate(DateUtil.now());
        itemRepository.save(itemToRemove);

        final String itemType = "item";
        final UserAction userAction = UserActionUtil.create(user, actionType, itemType, itemToRemove);
        userActionRepository.save(userAction);
    }

    private void removeItemFromAllParentItems(final Long idToRemove,
                                              final UserEntity user,
                                              final String actionType) {
        for (final Item item : findAll()) {
            final Set<Replacer> replacersToRemove = new HashSet<>();
            for (final Replacer replacer : item.getReplacers()) {
                final Item nestedItem = replacer.getItem();
                if (nestedItem.getId().equals(idToRemove)) {
                    replacersToRemove.add(replacer);
                    replacer.setStatus("deleted");
                    replacerRepository.save(replacer);

                    final UserAction userAction = UserActionUtil.create(user, actionType, nestedItem, replacer);
                    userActionRepository.save(userAction);
                }
            }
            //item.getReplacers().removeAll(replacersToRemove);

            final Set<ChildItem> partsToRemove = new HashSet<>();
            for (final ChildItem part : item.getChildItems()) {
                final Item nestedItem = part.getItem();
                if (nestedItem.getId().equals(idToRemove)) {
                    partsToRemove.add(part);
                    part.setStatus("deleted");
                    partRepository.save(part);

                    final UserAction userAction = UserActionUtil.create(user, actionType, nestedItem, part);
                    userActionRepository.save(userAction);
                }
            }
            //item.getChildItems().removeAll(partsToRemove);

            itemRepository.save(item);
        }
    }

    private Map<String, String> createHeaderMatrixMap(final ItemView itemView) {
        final TableDto header = itemView.getHeader();
        final String[][] headerMatrix = header.getMatrix();
        final Map<String, String> headerMatrixMap = new HashMap<>();
        for (final String[] row : headerMatrix) {
            headerMatrixMap.put(row[0], row[1]);
        }
        return headerMatrixMap;
    }

    private void updateDescription(final Item item, final Map<String, String> headerMatrixMap) {
        final String newDescription = ItemUtil.toDescription(headerMatrixMap);
        applyNewDescriptionToCategory(item.getCategory(), headerMatrixMap);
        item.setDescription(newDescription);
    }

    public void applyNewDescriptionToCategory(final String category, final Map<String, String> newDescriptionMap) {
        final List<Item> allItemsOfCategory = find(category);
        for (final Item item : allItemsOfCategory) {
            final Map<String, String> oldItemDescriptionMap = ItemUtil.toMap(item.getDescription());
            final Map<String, String> newItemDescriptionMap = new HashMap<>(newDescriptionMap);
            for (final Map.Entry<String, String> entry : newItemDescriptionMap.entrySet()) {
                final String newParameter = entry.getKey();
                final String value = oldItemDescriptionMap.get(newParameter);
                if (value == null) {
                    entry.setValue("-");
                } else {
                    entry.setValue(value);
                }
            }
            item.setDescription(ItemUtil.toDescription(newItemDescriptionMap));
        }
    }

    private void updateParts(final Item item, final ItemView itemView) {
        final Set<ChildItem> oldParts = new HashSet<>(item.getChildItems());
        final Set<ChildItem> newParts = new HashSet<>(createPartsFromItemView(item, itemView));
        item.getChildItems().clear();
        item.getChildItems().addAll(newParts);

        final List<ChildItem> toSave = new ArrayList<>();
        for (final ChildItem oldPart : oldParts) {
            for (final ChildItem newPart : newParts) {
                if (newPart.getName().equals(oldPart.getName())) {
                    toSave.add(oldPart);
                }
            }
        }
        oldParts.removeAll(toSave);
        for (final ChildItem orphan : oldParts) {
            partRepository.deleteById(orphan.getId());
        }
    }

    private void updateReplacers(final Item item, final ItemView itemView) {
        final Set<Replacer> oldReplacers = new HashSet<>(item.getReplacers());
        final Set<Replacer> newReplacers = new HashSet<>(createReplacersFromItemView(item, itemView));
        item.getReplacers().clear();
        item.getReplacers().addAll(newReplacers);

        final List<Replacer> toSave = new ArrayList<>();
        for (final Replacer oldReplacer : oldReplacers) {
            for (final Replacer newReplacer : newReplacers) {
                if (newReplacer.getName().equals(oldReplacer.getName())) {
                    toSave.add(oldReplacer);
                }
            }
        }
        oldReplacers.removeAll(toSave);
        for (final Replacer orphan : oldReplacers) {
            replacerRepository.deleteById(orphan.getId());
        }
    }

    private Set<ChildItem> createPartsFromItemView(final Item item, final ItemView itemView) {
        final List<NestedItemDto> allItems = NestedItemUtil.collectAllItems(itemView.getPartsTable());
        final List<NestedItemDto> preparedItems = prepareNestedItemDtosToConverting(allItems);

        final Set<ChildItem> partsFromItemView = new HashSet<>();
        for (final NestedItemDto nestedItem : preparedItems) {
            final Item partItem = getOne(nestedItem.getItemId());

            final ChildItem part = new ChildItem();
            part.setId(nestedItem.getId());
            part.setName(nestedItem.getName());
            part.setItem(partItem);
            part.setLocation(nestedItem.getLocation());
            part.setQuantity(nestedItem.getQuantity());
            part.setStatus(nestedItem.getStatus());

            partsFromItemView.add(part);
        }

        return partsFromItemView;
    }

    private Set<Replacer> createReplacersFromItemView(final Item item, final ItemView itemView) {
        final ReplacersTable replacersTable = itemView.getReplacersTable();
        final List<NestedItemDto> dtos = prepareNestedItemDtosToConverting(replacersTable.getReplacers());

        final Set<Replacer> replacersFromItemView = new HashSet<>();
        for (final NestedItemDto dto : dtos) {
            final Item replacerItem = getOne(dto.getItemId());

            final Replacer replacer = new Replacer();
            replacer.setId(dto.getId());
            replacer.setName(dto.getName());
            replacer.setItem(replacerItem);
            replacer.setComment(dto.getComment());
            replacer.setStatus(dto.getStatus());

            replacersFromItemView.add(replacer);
        }

        return replacersFromItemView;
    }

    private void updateName(final Item item, final Map<String, String> headerMatrixMap) {
        final String oldName = item.getName();
        final String newName = headerMatrixMap.get("Name");
        if (newName != null && !newName.equals(oldName)) {
            item.setName(newName);
            applyToAllItemDescriptions(item.getCategory(), oldName, newName);
        }
        headerMatrixMap.remove("Name");
    }

    private void applyToAllItemDescriptions(final String updatingItemCategory,
                                            final String oldValue,
                                            final String newValue) {
        final List<Item> items = findAll();
        final Set<String> categories = findCategories(items);
        for (final Item item : items) {
            final Map<String, String> descriptionMap = ItemUtil.toMap(item.getDescription());
            for (final Map.Entry<String, String> entry : descriptionMap.entrySet()) {
                final String value = entry.getValue().split(" \\(")[0];
                if (value.equals(oldValue)) {
                    if (categories.contains(entry.getKey())) {
                        if (entry.getKey().equals(updatingItemCategory)) {
                            entry.setValue(entry.getValue().replace(oldValue, newValue));
                        }
                    } else {
                        entry.setValue(entry.getValue().replace(oldValue, newValue));
                    }
                }
            }
            final String newDescription = ItemUtil.toDescription(descriptionMap);
            item.setDescription(newDescription);
            itemRepository.save(item);
        }
    }

    public ItemView createMotorcycleCatalogueView(final ItemView itemView) {
        final List<Item> motorcycles = find("Motorcycle");
        final String tableName = "Motorcycle catalogue";
        final String countParameterName = "Models";

        return ItemViewUtil.createPartsView(
                itemView,
                motorcycles.size(),
                tableName,
                countParameterName,
                TableUtil.motorcyclesTable(motorcycles, this, userService),
                this);
    }

    public Item getOrCreate(final TransitiveItem transitiveItem) {
        final Item item = find(transitiveItem.getCategory(), transitiveItem.getName());
        return item != null ? item : create(transitiveItem);
    }

    public Item create(final TransitiveItem transitiveItem) {
        final TransitiveItemDescriptionMap descriptionMap
                = createDescriptionMap(transitiveItem, transitiveItemService);
        final Map<String, String> items = descriptionMap.getItems();
        final List<ChildItem> childItems
                = ChildItemUtil.createParts(transitiveItem, items, this, transitiveItemService);
        final List<Replacer> replacers
                = ReplacerUtil.createReplacers(transitiveItem, this, transitiveItemService);

        final Item item = new Item();
        item.setName(transitiveItem.getName());
        item.setCategory(transitiveItem.getCategory().replace(" (i)", ""));
        item.setStatus("active");
        item.setDescription(createItemDescription(transitiveItem));
        item.getChildItems().addAll(childItems);
        item.getReplacers().addAll(replacers);
        item.setCreatorId(userService.getAdmin().getId());
        item.setUserActionDate(DateUtil.now());
        return item;
    }

    public String createItemDescription(final TransitiveItem transitiveItem) {
        final TransitiveItemDescriptionMap descriptionMap = createDescriptionMap(transitiveItem, transitiveItemService);
        descriptionMap.getItems().clear();
        return ItemUtil.toDescription(descriptionMap);
    }

    public Set<String> findCategories(final List<Item> items) {
        final Set<String> categories = new HashSet<>();
        for (final Item item : items) {
            categories.add(item.getCategory());
        }
        return categories;
    }

    public Set<String> findAllCategories() {
        return findCategories(findAll());
    }

    public Set<String> findAllPartCategories() {
        return CategoryUtil.filterPartCategories(findAllCategories());
    }

}
