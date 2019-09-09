package com.pazukdev.backend.dataloader;

import com.pazukdev.backend.dto.item.ReplacerData;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.entity.AbstractEntityFactory;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.entity.item.ItemFactory;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.util.ItemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 *
 * the class populates all empty tables in db with default data on app startup
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final ItemFactory itemFactory;
    private final ItemService itemService;

    @Override
    public void run(ApplicationArguments args) {
        populateEmptyTables();
    }

    private void populateEmptyTables() {
        if (repositoryIsEmpty(itemService.getRepository())) {
            createAll(itemFactory, itemService.getRepository());
        }
    }

    private <Entity extends AbstractEntity> Boolean repositoryIsEmpty(final JpaRepository<Entity, Long> repository) {
        return repository.findAll().isEmpty();
    }

    private <Entity extends AbstractEntity> void createAll(final AbstractEntityFactory<Entity> factory,
                                                           final JpaRepository<Entity, Long> repository) {
        final List<Entity> entities = factory.createEntitiesFromCSVFile();
        for (final Entity entity : entities) {
            repository.save(entity);
        }
        createStubReplacers(itemService.findAll());
    }

    private void createStubReplacers(final List<ItemEntity> items) {
        for (final ItemEntity item : items) {
            createStubReplacers(item);
        }
    }

    private void createStubReplacers(final ItemEntity item) {
        final List<ReplacerData> replacersData = ItemUtil.parseReplacersSourceString(item.getReplacer());
        for (final ReplacerData replacerData : replacersData) {
            final ItemEntity replacer = itemService.find(item.getCategory(), replacerData.getName());
            if (replacer == null) {
                final ItemEntity stubReplacer = new ItemEntity();
                stubReplacer.setName(replacerData.getName());
                stubReplacer.setReplacer("-");
                stubReplacer.setDescription(item.getDescription());
                stubReplacer.setCategory(item.getCategory());
                itemService.getRepository().save(stubReplacer);
            }
        }
    }

}
















