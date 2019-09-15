package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.dto.item.ItemDescriptionMap;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.entity.item.ChildItem;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.entity.item.TransitiveItem;
import com.pazukdev.backend.repository.ItemRepository;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.SpecificStringUtil;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pazukdev.backend.util.ItemUtil.createDescriptionMap;
import static com.pazukdev.backend.util.SpecificStringUtil.replaceBlankWithDash;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ItemService extends AbstractService<Item, ItemDto> {

    final TransitiveItemService transitiveItemService;
    @Getter
    private final ItemRepository itemRepository;

    public ItemService(final ItemRepository itemRepository,
                       final ItemConverter converter,
                       final TransitiveItemService transitiveItemService) {
        super(itemRepository, converter);
        this.transitiveItemService = transitiveItemService;
        this.itemRepository = itemRepository;
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
        final List<Item> categorizedItems = new ArrayList<>();
        for (final Item item : findAll()) {
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

    public Item getOrCreate(final TransitiveItem transitiveItem) {
        final Item item = find(transitiveItem.getCategory(), transitiveItem.getName());
        return item != null ? item : create(transitiveItem);
    }

    public Item create(final TransitiveItem transitiveItem) {
        final Item item = new Item();
        item.setName(transitiveItem.getName());
        item.setCategory(transitiveItem.getCategory());
        item.setDescription(createItemDescription(transitiveItem));
        item.setReplacer(transitiveItem.getReplacer());
        item.getChildItems().addAll(createChildItems(transitiveItem));
        return item;
    }

    public String createItemDescription(final TransitiveItem transitiveItem) {
        final ItemDescriptionMap descriptionMap = createDescriptionMap(transitiveItem, transitiveItemService);
        descriptionMap.getItems().clear();
        return ItemUtil.toDescription(descriptionMap);
    }

    public List<ChildItem> createChildItems(final TransitiveItem parent) {
        final ItemDescriptionMap descriptionMap = createDescriptionMap(parent, transitiveItemService);
        final List<ChildItem> childItems = new ArrayList<>();
        for (final Map.Entry entry : descriptionMap.getItems().entrySet()) {
            final String category = entry.getKey().toString();
            if (entry.getValue().toString().contains(";")) {
                final String[] names = entry.getValue().toString().split("; ");
                for (final String name : names) {
                    final ChildItem childItem = createChildItem(parent, name, category);
                    if (childItem != null) {
                        childItems.add(childItem);
                    }
                }
            } else {
                final String name = entry.getValue().toString();
                final ChildItem childItem = createChildItem(parent, name, category);
                if (childItem != null) {
                    childItems.add(childItem);
                }
            }
        }

        return childItems;
    }

    public ChildItem createChildItem(final TransitiveItem parent, final String value, final String category) {
        String name;
        String location = "";
        String quantity;
        if (SpecificStringUtil.containsParentheses(value)) {
            name = SpecificStringUtil.getStringBeforeParentheses(value);
            String additionalData = SpecificStringUtil.getStringBetweenParentheses(value);
            location = additionalData.contains(" - ") ? additionalData.split(" - ")[0] : "-";
            quantity = additionalData.contains(" - ") ? additionalData.split(" - ")[1] : additionalData;
        } else {
            name = value;
            location = "-";
            quantity = category.equals("Spark plug") ? "2" : "1";
        }
        final TransitiveItem oldChild = category.equals("Seal")
                ? transitiveItemService.getUssrSealBySize(name)
                : transitiveItemService.find(category, name);

        if (oldChild != null) {
            Item child = getOrCreate(oldChild);

            final ChildItem childItem = new ChildItem();
            childItem.setName(parent.getName() + " - " + name);
            childItem.setItem(child);
            childItem.setLocation(location);
            childItem.setQuantity(quantity);
            return childItem;
        } else {
            return null;
        }
    }

}
