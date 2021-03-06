package com.pazukdev.backend.service;

import com.pazukdev.backend.constant.Status;
import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.dto.PossibleNestedItemsDto;
import com.pazukdev.backend.dto.RateReplacer;
import com.pazukdev.backend.dto.TransitiveItemDescriptionMap;
import com.pazukdev.backend.dto.TransitiveItemDto;
import com.pazukdev.backend.dto.factory.ItemViewFactory;
import com.pazukdev.backend.dto.view.ItemView;
import com.pazukdev.backend.entity.Item;
import com.pazukdev.backend.entity.NestedItem;
import com.pazukdev.backend.entity.TransitiveItem;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.*;
import com.pazukdev.backend.util.*;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.pazukdev.backend.entity.NestedItem.Type;
import static com.pazukdev.backend.util.CategoryUtil.Category.MATERIAL;
import static com.pazukdev.backend.util.CategoryUtil.Parameter.INSULATION;
import static com.pazukdev.backend.util.CategoryUtil.isInfo;
import static com.pazukdev.backend.util.ItemUtil.*;

/**
 * @author Siarhei Sviarkaltsau
 */
@Getter
@Service
public class ItemService extends AbstractService<Item, TransitiveItemDto> {

    private final List<String> categoriesToCache = Arrays.asList(
            "Vehicle", "Engine", "Gearbox", "Final drive", "Reduction drive", "Generator", "Chassis");

    private final UserService userService;
    private final NestedItemRepository nestedItemRepo;
    private final UserActionRepository userActionRepo;
    private final ItemRepository itemRepository;
    private final AdminMessageRepository adminMessageRepo;
    private final LinkRepository linkRepository;
    private final EmailSenderService emailSenderService;
    private final Map<String, ItemView> cachedViews = new HashMap<>();

    public ItemService(final ItemRepository itemRepository,
                       final ItemConverter converter,
                       final NestedItemRepository nestedItemRepo,
                       final UserService userService,
                       final UserActionRepository userActionRepo,
                       final AdminMessageRepository adminMessageRepo,
                       final LinkRepository linkRepository,
                       final EmailSenderService emailSenderService) {
        super(itemRepository, converter);
        this.nestedItemRepo = nestedItemRepo;
        this.userService = userService;
        this.userActionRepo = userActionRepo;
        this.itemRepository = itemRepository;
        this.adminMessageRepo = adminMessageRepo;
        this.linkRepository = linkRepository;
        this.emailSenderService = emailSenderService;
    }

    @Transactional
    @Override
    public Item findFirstByName(final String name) {
        return itemRepository.findFirstByName(name);
    }

    @Transactional
    public Item findFirstByCategoryAndName(final String category, final String name) {
        return itemRepository.findFirstByCategoryAndNameAndStatus(category, name, Status.ACTIVE);
    }

    @Transactional
    public List<Item> find(final String... categories) {
        final List<Item> items = new ArrayList<>();
        for (final String category : categories) {
            items.addAll(find(category, findAllActive()));
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
    public Item convertTransitiveItemToItem(final TransitiveItem transitiveItem,
                                            final List<TransitiveItem> transitiveItems,
                                            final List<String> infoCategories,
                                            final UserEntity admin,
                                            final boolean initialDBPopulation,
                                            final Map<Item, List<NestedItem>> itemsReplacers) {
        final String category = transitiveItem.getCategory();
        final String name = transitiveItem.getName();

        Item item = findFirstByCategoryAndName(category, name);
        if (item != null && initialDBPopulation) {
            return item;
        }
        if (item == null) {
            item = new Item();
        }

        final TransitiveItemDescriptionMap map = createDescriptionMap(transitiveItem, transitiveItems, infoCategories);
        final Map<String, String> items = new HashMap<>(map.getItems());
        final List<NestedItem> parts = ChildItemUtil.create(Type.PART, transitiveItem, items, this, transitiveItems, infoCategories, admin, itemsReplacers);
        final List<NestedItem> replacers = ChildItemUtil.create(Type.REPLACER, transitiveItem, null, this, transitiveItems, infoCategories, admin, itemsReplacers);

        item.getParts().clear();
        item.getReplacers().clear();

        item.setName(name);
        item.setCategory(category);
        item.setStatus(transitiveItem.getStatus());
        item.setDescription(createItemDescription(map));
        item.getParts().addAll(parts);
        item.getReplacers().addAll(replacers);
        item.setCreatorId(admin.getId());
        item.setUserActionDate(DateUtil.now());
        item.setImg(transitiveItem.getImage());
        LinkUtil.addLinksToItem(item, transitiveItem, null);

        itemRepository.save(item);
        itemsReplacers.put(item, replacers);
        return item;
    }

    public ItemView getCachedView(final String id, final String lang) {
        ItemView view = cachedViews.get(createKey(id, lang));
        if (view == null) {
            view = cachedViews.get(createKey(id, "en"));
        }
        return view;
    }

    public void putCachedView(final ItemView view, String lang) {
//        lang = "en"; // cache views only in English
        cachedViews.put(createKey(view.getItemId(), lang), view);
    }

    public void removeCachedView(final String id, final String lang) {
//        final Map<String, ItemView> cachedViews = new HashMap<>(this.cachedViews);
//        if (lang.equals("all")) {
//            for (final Map.Entry<String, ItemView> entry : cachedViews.entrySet()) {
//                if (entry.getKey().contains(id)) {
//                    this.cachedViews.remove(entry.getKey());
//                }
//            }
//        }
        cachedViews.clear();
//        cachedViews.entrySet().removeIf(e -> e.getKey().contains(id));
//        cachedViews.remove(createKey(id, lang));
    }

    private String createKey(final String id, final String lang) {
        return id + lang;
    }

    private String createItemDescription(final TransitiveItemDescriptionMap descriptionMap) {
        descriptionMap.getItems().clear();
        return toDescription(descriptionMap);
    }

    @Transactional
    public ItemView createHomeView(final String userName,
                                   final UserEntity user,
                                   final String language,
                                   ItemViewFactory factory) {
        if (factory == null) {
            factory = createNewItemViewFactory();
        }
        return factory.createHomeView(userName, user, language);
    }

    @Transactional
    public ItemView createItemsListView(final String itemsStatus,
                                        final String userName,
                                        final UserEntity user,
                                        final String language,
                                        ItemViewFactory factory) {
        if (factory == null) {
            factory = createNewItemViewFactory();
        }
        return factory.createItemsListView(itemsStatus, userName, user, language);
    }

    @Transactional
    public ItemView createWishlistView(final String userName,
                                       final UserEntity user,
                                       final String language) {
        return createNewItemViewFactory().createWishlistView(userName, user, language);
    }

    @Transactional
    public ItemView createItemView(final String itemId,
                                   final String userName,
                                   final UserEntity user,
                                   final String lang,
                                   final String option,
                                   ItemViewFactory factory) {
        if (factory == null) {
            factory = createNewItemViewFactory();
        }
        return factory.createItemView(itemId, Status.ACTIVE, userName, user, lang, option);
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
    public ItemView updateItemView(final String itemId,
                                   final String userName,
                                   final String language,
                                   final ItemView itemView) {
        return createNewItemViewFactory().updateItemView(itemId, userName, language, itemView);
    }

    @Transactional
    public PossibleNestedItemsDto getEditData(final Long itemId) {
        final List<Item> allItems = findAllActive();
        final Item parent = findFirst(itemId);
        return PossibleNestedItemsDto.create(allItems, parent, FileUtil.getInfoCategories(), this);
    }

    @Transactional
    public RateReplacer rateReplacer(final String userName, final RateReplacer rate) {
        final UserEntity user = userService.findFirstByName(userName);
        return RateUtil.rateReplacer(rate, user, this);
    }

    public Set<String> collectCategories(final List<Item> items) {
        final Set<String> categories = new HashSet<>();
        for (final Item item : items) {
            if (!item.getStatus().equals(Status.ACTIVE)) {
                continue;
            }
            categories.add(item.getCategory());
        }
        return categories;
    }

    public ItemViewFactory createNewItemViewFactory() {
        return new ItemViewFactory(this, FileUtil.getInfoCategories(), emailSenderService);
    }

    public List<Item> findParents(final Item item,
                                  final List<Item> checkList,
                                  final List<String> infoCategories) {
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
                        final Item foundItem = findFirstByCategoryAndName(category, value);
                        if (foundItem != null && foundItem.getId().equals(itemId)) {
                            parents.add(parent);
                            break;
                        }
                    }

                }
            }
        } else {
            for (final Item parent : checkList) {
                for (final NestedItem child : parent.getParts()) {
                    if (child.getItem().getId().equals(itemId)) {
                        parents.add(parent);
                    }
                }
            }
        }
        return parents;
    }

//    @Transactional
//    public List<ItemView> createViews(final List<Item> items)  {
//        final ItemViewFactory factory = createNewItemViewFactory();
//        final List<ItemView> views = new ArrayList<>();
//        items.forEach(item -> {
//            final List<Item> copy = new ArrayList<>(items);
//            copy.remove(item);
//            views.add(factory.createItemViewForCache(
//                    item,
//                    copy,
//                    FileUtil.getComments(),
//                    userService));
//        });
//        return views;
//    }

    public void setTime(final ItemView view,
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
