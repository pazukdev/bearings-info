package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.converter.ReplacerConverter;
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
import com.pazukdev.backend.util.*;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.pazukdev.backend.util.CategoryUtil.Category.Info.MATERIAL;
import static com.pazukdev.backend.util.CategoryUtil.Parameter.INSULATION;
import static com.pazukdev.backend.util.CategoryUtil.*;
import static com.pazukdev.backend.util.ChildItemUtil.createParts;
import static com.pazukdev.backend.util.ItemUtil.createDescriptionMap;
import static com.pazukdev.backend.util.ReplacerUtil.createReplacers;
import static com.pazukdev.backend.util.SpecificStringUtil.replaceBlankWithDash;

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
    }

    @Transactional
    @Override
    public Item findByName(String name) {
        return ((ItemRepository) repository).findByName(name);
    }

    public List<Item> findAllInfoItems() {
        return find(getInfoCategories().toArray(new String[0]));
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
    public Item saveAsItem(final TransitiveItem transitiveItem) {
        final Item item = getOrCreate(transitiveItem);
        item.setName(replaceBlankWithDash(item.getName()));
        if (item.getImg() == null) {
            item.setImg("-");
        }
        return repository.save(item);
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
        final ItemViewFactory itemViewFactory = new ItemViewFactory(this);
        try {
            return itemViewFactory.createNewItemView(category, name, userName, userLanguage);
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
        final ItemViewFactory itemViewFactory = new ItemViewFactory(this);
        return itemViewFactory.updateItemView(itemId, userName, language, itemView);
    }

    @Transactional
    public RateReplacer rateReplacer(final String userName, final RateReplacer rate) {
        final UserEntity user = userService.findByName(userName);
        return RateUtil.rateReplacer(rate, user, this);
    }

    public Item getOrCreate(final TransitiveItem transitiveItem) {
        final Item item = find(transitiveItem.getCategory(), transitiveItem.getName());
        return item != null ? item : create(transitiveItem);
    }

    public Item create(final TransitiveItem transitiveItem) {
        final TransitiveItemDescriptionMap descriptionMap = createDescriptionMap(transitiveItem, transitiveItemService);
        final Map<String, String> items = descriptionMap.getItems();
        final List<ChildItem> childItems = createParts(transitiveItem, items, this, transitiveItemService);
        final List<Replacer> replacers = createReplacers(transitiveItem, this, transitiveItemService);

        final String name = transitiveItem.getName();
        final Long soyuzRetromechanicId = 5L;
        final Long adminId = userService.getAdmin().getId();
        final Long creatorId = name.toLowerCase().contains("soyuz retromechanic") ? soyuzRetromechanicId : adminId;

        final Item item = new Item();
        item.setName(transitiveItem.getName());
        item.setCategory(transitiveItem.getCategory());
        item.setStatus("active");
        item.setDescription(createItemDescription(descriptionMap));
        item.getChildItems().addAll(childItems);
        item.getReplacers().addAll(replacers);
        item.setCreatorId(creatorId);
        item.setUserActionDate(DateUtil.now());
        item.setImg(transitiveItem.getImage());
        LinkUtil.addLinksToItem(item, transitiveItem);
        return item;
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

    @Transactional
    public List<Item> findVehicles() {
        final List<Item> items = new ArrayList<>();
        for (final Item item : findAll()) {
            if (isVehicle(item.getCategory())) {
                items.add(item);
            }
        }
        return items;
    }

    public Set<String> findAllPartCategories() {
        return filterPartCategories(findAllCategories());
    }

    private String createItemDescription(final TransitiveItemDescriptionMap descriptionMap) {
        descriptionMap.getItems().clear();
        return ItemUtil.toDescription(descriptionMap);
    }

    private ItemViewFactory createNewItemViewFactory() {
        return new ItemViewFactory(this);
    }

    public List<Item> getParents(final Item item) {
        final Long itemId = item.getId();
        final boolean infoItem = CategoryUtil.isInfo(item.getCategory());
        final List<Item> parents = new ArrayList<>();

        if (infoItem) {
            for (final Item parent : findAll()) {
                for (final Map.Entry<String, String> entry : ItemUtil.toMap(parent.getDescription()).entrySet()) {
                    String parameter = entry.getKey();
                    String value = entry.getValue();
                    // str.matches(".*\\d
                    String category = parameter.replaceAll("[0-9]","").trim();
                    if (category.equalsIgnoreCase(INSULATION)) {
                        category = MATERIAL;
                    }
                    final Item foundItem = find(category, value);
                    if (foundItem != null && foundItem.getId().equals(itemId)) {
                        parents.add(parent);
                    }
                }
            }
        } else {
            for (final Item parent : findAll()) {
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
