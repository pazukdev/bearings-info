package com.pazukdev.backend.service;

import com.pazukdev.backend.constant.Status;
import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.converter.ReplacerConverter;
import com.pazukdev.backend.dto.AdminMessage;
import com.pazukdev.backend.dto.RateReplacer;
import com.pazukdev.backend.dto.TransitiveItemDescriptionMap;
import com.pazukdev.backend.dto.TransitiveItemDto;
import com.pazukdev.backend.dto.factory.ItemViewFactory;
import com.pazukdev.backend.dto.view.ItemView;
import com.pazukdev.backend.entity.*;
import com.pazukdev.backend.repository.ChildItemRepository;
import com.pazukdev.backend.repository.ItemRepository;
import com.pazukdev.backend.repository.ReplacerRepository;
import com.pazukdev.backend.repository.UserActionRepository;
import com.pazukdev.backend.util.DateUtil;
import com.pazukdev.backend.util.LinkUtil;
import com.pazukdev.backend.util.RateUtil;
import com.pazukdev.backend.util.UserActionUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.pazukdev.backend.util.CategoryUtil.Category.*;
import static com.pazukdev.backend.util.CategoryUtil.Parameter.INSULATION;
import static com.pazukdev.backend.util.CategoryUtil.isInfo;
import static com.pazukdev.backend.util.ChildItemUtil.createParts;
import static com.pazukdev.backend.util.FileUtil.FileName.INFO_CATEGORIES;
import static com.pazukdev.backend.util.FileUtil.getTxtFileLines;
import static com.pazukdev.backend.util.ItemUtil.*;
import static com.pazukdev.backend.util.ReplacerUtil.createReplacers;
import static com.pazukdev.backend.util.UserActionUtil.processItemAction;
import static com.pazukdev.backend.util.UserActionUtil.processReplacerAction;

/**
 * @author Siarhei Sviarkaltsau
 */
@Getter
@Service
public class ItemService extends AbstractService<Item, TransitiveItemDto> {

    private final TransitiveItemService transitiveItemService;
    private final UserService userService;
    private final ChildItemRepository childItemRepository;
    private final UserActionRepository userActionRepository;
    private final ReplacerRepository replacerRepository;
    private final ReplacerConverter replacerConverter;
    private final ItemRepository itemRepository;
    @Setter
    private AdminMessage adminMessage;

    private int bearingReplacerCounter = 0;
    private int sealReplacerCounter = 0;
    private int oilFilterReplacerCounter = 0;
    private int sparkPlugReplacerCounter = 0;

    public ItemService(final ItemRepository itemRepository,
                       final ItemConverter converter,
                       final TransitiveItemService transitiveItemService,
                       final ChildItemRepository childItemRepository,
                       final UserService userService,
                       final UserActionRepository userActionRepository,
                       final ReplacerRepository replacerRepository,
                       final ReplacerConverter replacerConverter) {
        super(itemRepository, converter);
        this.transitiveItemService = transitiveItemService;
        this.childItemRepository = childItemRepository;
        this.userService = userService;
        this.userActionRepository = userActionRepository;
        this.replacerRepository = replacerRepository;
        this.replacerConverter = replacerConverter;
        this.itemRepository = itemRepository;
    }

    @Transactional
    @Override
    public Item findByName(String name) {
        return itemRepository.findByName(name);
    }

//    public List<Item> findAllInfoItems() {
//        return find(getInfoCategories().toArray(new String[0]));
//    }

    @Transactional
    public Item find(final String category, final String name) {
        Item item = itemRepository.findFirstByCategoryAndName(category, name);
        if (item != null && !item.getStatus().equals(Status.ACTIVE)) {
            find(category, name);
        }
        return item;
    }

    @Transactional
    @Override
    public List<Item> findAll() {
        final List<Item> items = itemRepository.findAll();
        items.removeIf(entity -> !entity.getStatus().equals(Status.ACTIVE));
        return items;
    }

    @Transactional
    public List<Item> find(final String... categories) {
        final List<Item> items = new ArrayList<>();
        for (final String category : categories) {
            items.addAll(find(category, findAll()));
        }
        return items;
    }

    @Transactional
    public List<Item> find(final String category, final List<Item> items) {
        final List<Item> categorizedItems = new ArrayList<>();
        for (final Item item : items) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                categorizedItems.add(item);
            }
        }
        return categorizedItems;
    }

    @Transactional
    public Item create(final TransitiveItem transitiveItem, final Set<String> infoCategories) {
        final String category = transitiveItem.getCategory();
        final String name = transitiveItem.getName();

        final Item alreadyExistingItem = find(category, name);
        if (alreadyExistingItem != null) {
            return alreadyExistingItem;
        }

        final TransitiveItemDescriptionMap descriptionMap
                = createDescriptionMap(transitiveItem, transitiveItemService, infoCategories);
        final Map<String, String> items = descriptionMap.getItems();
        final List<ChildItem> childItems = createParts(transitiveItem, items, this, transitiveItemService, infoCategories);
        final List<Replacer> replacers = createReplacers(transitiveItem, this, transitiveItemService, infoCategories);

        final Long soyuzRetromechanicId = 5L;
        final Long adminId = userService.getAdmin().getId();
        final Long creatorId = name.toLowerCase().contains("soyuz retromechanic") ? soyuzRetromechanicId : adminId;

        final Item newItem = new Item();
        newItem.setName(name);
        newItem.setCategory(category);
        newItem.setStatus(Status.ACTIVE);
        newItem.setDescription(createItemDescription(descriptionMap));
        newItem.getChildItems().addAll(childItems);
        newItem.getReplacers().addAll(replacers);
        newItem.setCreatorId(creatorId);
        newItem.setUserActionDate(DateUtil.now());
        newItem.setImg(transitiveItem.getImage());
        LinkUtil.addLinksToItem(newItem, transitiveItem);

        itemRepository.save(newItem);

        if (category.equals(VEHICLE)) {
            processItemAction(UserActionUtil.ActionType.CREATE, newItem, getUserService().getAdmin(), this);
        }

        for (final Replacer replacer : replacers) {
            if ((category.equals(SEAL) && sealReplacerCounter++ < 3)
                    || (category.equals(SPARK_PLUG) && sparkPlugReplacerCounter++ < 2)
                    || (category.equals(BEARING) && bearingReplacerCounter++ < 2)
                    || (category.equals(OIL_FILTER) && oilFilterReplacerCounter++ < 3)) {
                processReplacerAction(UserActionUtil.ActionType.ADD, replacer, newItem, getUserService().getAdmin(), this);
            }
        }

        return newItem;
    }

    @Transactional
    public ItemView createHomeView(final String userName, final String language) {
        return createNewItemViewFactory().createHomeView(userName, language);
    }

    @Transactional
    public ItemView createItemsManagementView(final String userName, final String language) {
        return createNewItemViewFactory().createItemsManagementView(userName, language);
    }

    @Transactional
    public ItemView createWishlistView(final String userName, final String language) {
        return createNewItemViewFactory().createWishlistView(userName, language);
    }

    @Transactional
    public ItemView createItemView(final Long itemId, final String userName, final String language) {
        return createNewItemViewFactory().createItemView(itemId, userName, language);
    }

    @Transactional
    public ItemView createNewItemView(final String category,
                                      final String name,
                                      final String userName,
                                      final String userLanguage) {
        try {
            return createNewItemViewFactory().createNewItemView(category, name, userName, userLanguage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public ItemView updateItemView(final Long itemId,
                                   final String userName,
                                   final String language,
                                   final ItemView itemView) {
        return createNewItemViewFactory().updateItemView(itemId, userName, language, itemView);
    }

    @Transactional
    public RateReplacer rateReplacer(final String userName, final RateReplacer rate) {
        final UserEntity user = userService.findByName(userName);
        return RateUtil.rateReplacer(rate, user, this);
    }

    public Set<String> collectCategories(final List<Item> items) {
        final Set<String> categories = new HashSet<>();
        for (final Item item : items) {
            categories.add(item.getCategory());
        }
        return categories;
    }

    private String createItemDescription(final TransitiveItemDescriptionMap descriptionMap) {
        descriptionMap.getItems().clear();
        return toDescription(descriptionMap);
    }

    private ItemViewFactory createNewItemViewFactory() {
        return new ItemViewFactory(this, getTxtFileLines(INFO_CATEGORIES));
    }

    public List<Item> findParents(final Item item,
                                  final List<Item> checkList,
                                  final Set<String> infoCategories) {
        final Long itemId = item.getId();
        final String category = item.getCategory();
        String secondSearchCategory = null;
        if (category.equalsIgnoreCase(MATERIAL)) {
            secondSearchCategory = INSULATION;
        }
        final List<Item> parents = new ArrayList<>();

        if (isInfo(category, infoCategories)) {
            for (final Item parent : checkList) {
                final String description = parent.getDescription();
                if (!description.contains(category)
                        && (secondSearchCategory == null || !description.contains(secondSearchCategory))) {
                    continue;
                }
                for (final Map.Entry<String, String> entry : toMap(description).entrySet()) {
                    String parameter = entry.getKey();
                    if (!parameter.equals(category) && !parameter.equals(secondSearchCategory)) {
                        continue;
                    }
                    for (final String value : entry.getValue().split("; ")) {
                        final Item foundItem = find(category, value);
                        if (foundItem != null && foundItem.getId().equals(itemId)) {
                            parents.add(parent);
                            break;
                        }
                    }

                }
            }
        } else {
            for (final Item parent : checkList) {
                for (final ChildItem child : parent.getChildItems()) {
                    if (child.getItem().getId().equals(itemId)) {
                        parents.add(parent);
                    }
                }
            }
        }
        return parents;
    }

}
