package com.pazukdev.backend.dataloader;

import com.pazukdev.backend.dto.item.ReplacerData;
import com.pazukdev.backend.dto.item.TransitiveItemDescriptionMap;
import com.pazukdev.backend.entity.item.TransitiveItem;
import com.pazukdev.backend.entity.item.TransitiveItemFactory;
import com.pazukdev.backend.repository.ChildItemRepository;
import com.pazukdev.backend.repository.TransitiveItemRepository;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.TransitiveItemService;
import com.pazukdev.backend.util.ItemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 *
 * the class populates all empty tables in db with default data on app startup
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final TransitiveItemFactory transitiveItemFactory;
    private final TransitiveItemService transitiveItemService;
    private final ItemService itemService;
    private final ChildItemRepository childItemRepository;

    @Override
    public void run(ApplicationArguments args) {
        populateEmptyTables();
    }

    private void populateEmptyTables() {
        if (repositoryIsEmpty(transitiveItemService.getTransitiveItemRepository())) {
            createTransitiveItems();
            createItems();
        }
    }

    private boolean repositoryIsEmpty(final TransitiveItemRepository repository) {
        return repository.findAll().isEmpty();
    }

    private void createTransitiveItems() {
        final List<TransitiveItem> transitiveItems = transitiveItemFactory.createEntitiesFromCSVFile();
        saveTransitiveItems(transitiveItems);
        createStubReplacers(transitiveItems);
        createStubInfoItems(transitiveItems);
    }

    private void createItems() {
        final List<TransitiveItem> transitiveItems = transitiveItemService.findAll();
        for (final TransitiveItem transitiveItem : transitiveItems) {
            itemService.saveAsItem(transitiveItem);
        }
    }

    private void saveTransitiveItems(final List<TransitiveItem> items) {
        for (final TransitiveItem item : items) {
            transitiveItemService.getTransitiveItemRepository().save(item);
        }
    }

    private void createStubReplacers(final List<TransitiveItem> items) {
        for (final TransitiveItem item : items) {
            createStubReplacers(item);
        }
    }

    private void createStubInfoItems(final List<TransitiveItem> items) {
        for (final TransitiveItem item : items) {
            createStubInfoItems(item);
        }
    }

    private void createStubInfoItems(final TransitiveItem item) {
        final TransitiveItemDescriptionMap descriptionMap = ItemUtil.createDescriptionMap(item, transitiveItemService);
        for (final Map.Entry<String, String> entry : descriptionMap.getSelectableCharacteristics().entrySet()) {
            final String category = entry.getKey();
            final String name = entry.getValue();
            if (transitiveItemService.find(category + " (i)", name) == null) {
                final TransitiveItem stub = new TransitiveItem();
                stub.setName(name);
                stub.setCategory(category + " (i)");
                transitiveItemService.getTransitiveItemRepository().save(stub);
            }
        }
    }

    private void createStubReplacers(final TransitiveItem item) {
        final List<ReplacerData> replacersData = ItemUtil.parseReplacersSourceString(item.getReplacer());
        for (final ReplacerData replacerData : replacersData) {
            final TransitiveItem replacer = transitiveItemService.find(item.getCategory(), replacerData.getName());
            if (replacer == null) {
                final TransitiveItem stubReplacer = new TransitiveItem();
                stubReplacer.setName(replacerData.getName());
                stubReplacer.setReplacer("-");
                stubReplacer.setDescription(item.getDescription());
                stubReplacer.setCategory(item.getCategory());
                transitiveItemService.getTransitiveItemRepository().save(stubReplacer);
            }
        }
    }

}
















