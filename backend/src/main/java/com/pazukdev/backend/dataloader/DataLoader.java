package com.pazukdev.backend.dataloader;

import com.pazukdev.backend.dto.ReplacerData;
import com.pazukdev.backend.entity.TransitiveItem;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.entity.factory.TransitiveItemFactory;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.service.TransitiveItemUtil;
import com.pazukdev.backend.util.BearingUtil;
import com.pazukdev.backend.util.FileUtil;
import com.pazukdev.backend.util.ItemUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private static final Logger LOG = LoggerFactory.getLogger("DataLoader");

    private final TransitiveItemFactory transitiveItemFactory;
    @Getter
    private final ItemService itemService;

    @Override
    public void run(ApplicationArguments args) {
        final boolean initialDBPopulation = true;
        populate(initialDBPopulation);
    }

    @Transactional
    public void populate(final boolean initialDBPopulation) {
        if (initialDBPopulation && !itemService.getRepository().findAll().isEmpty()) {
            return;
        }
        final long start = System.nanoTime();

        final List<String> infoCategories = FileUtil.readGoogleDocDocument(FileUtil.FileId.INFO_CATEGORY);

        final List<UserEntity> users = itemService.getUserService().findAll();
        if (initialDBPopulation) {
            users.addAll(itemService.getUserService().getUsersFromRecoveryFile(true));
        }
        final List<TransitiveItem> transitiveItems = createStubReplacers(transitiveItemFactory.createEntitiesFromCSVFile());
        final UserEntity admin = itemService.getUserService().findAdmin(users);
        for (final TransitiveItem transitiveItem : transitiveItems) {
            itemService.convertTransitiveItemToItem(transitiveItem, transitiveItems, infoCategories, users, admin, initialDBPopulation);
        }
        itemService.getUserService().recoverUserActions(users, itemService);
        itemService.getUserService().save(users);

        final long stop = System.nanoTime();
        final double time = (stop - start) * 0.000000001;
        LOG.info("DB created in " + (int) time + " seconds");
    }

    private List<TransitiveItem> createStubReplacers(final List<TransitiveItem> items) {
        final List<TransitiveItem> stubReplacers = new ArrayList<>();
        for (final TransitiveItem item : items) {
            final List<TransitiveItem> newItems = createStubReplacers(item, items);
            stubReplacers.addAll(newItems);
        }
        items.addAll(stubReplacers);
        return items;
    }

    private List<TransitiveItem> createStubReplacers(final TransitiveItem item, final List<TransitiveItem> items) {
        final List<TransitiveItem> newItems = new ArrayList<>();
        final String category = item.getCategory();
        final Map<String, String> descriptionMap = ItemUtil.toMap(item.getDescription());
        final List<ReplacerData> replacersData = ItemUtil.parseReplacersSourceString(item.getReplacer());

        for (final ReplacerData replacerData : replacersData) {
            final String name = replacerData.getName();
            TransitiveItem replacer = TransitiveItemUtil.findFirstByCategoryAndName(category, name, items);
            if (replacer == null && category.equals("Rubber part")) {
                replacer = TransitiveItemUtil.findFirstByCategoryAndName("Bearing", name, items);
            }
            if (replacer == null) {
                final TransitiveItem stubReplacer = new TransitiveItem();
                stubReplacer.setName(replacerData.getName());
                stubReplacer.setReplacer("-");
                stubReplacer.setCategory(category);
                stubReplacer.setImage("-");
                if (category.equalsIgnoreCase("bearing")) {
                    BearingUtil.setBearingEnclosure(stubReplacer);
                    removeValues(descriptionMap, "Manufacturer", "Standard");
                } else {
                    removeValues(descriptionMap, "Manufacturer", "Standard", "Material", "Screw class");
                }

                stubReplacer.setDescription(ItemUtil.toDescription(descriptionMap));
                newItems.add(stubReplacer);
            }
        }
        return newItems;
    }

    private void removeValues(final Map<String, String> descriptionMap, final String... keys) {
        for (final String key : keys) {
            if (descriptionMap.get(key) != null) {
                descriptionMap.put(key, "-");
            }
        }
    }

}
















