package com.pazukdev.backend.dataloader;

import com.pazukdev.backend.dto.item.ReplacerData;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.entity.AbstractEntityFactory;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.entity.item.ItemFactory;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.bearing.BearingFactory;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleFactory;
import com.pazukdev.backend.entity.product.oil.OilFactory;
import com.pazukdev.backend.entity.product.seal.SealFactory;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugFactory;
import com.pazukdev.backend.entity.product.unit.engine.EngineFactory;
import com.pazukdev.backend.repository.*;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.util.ItemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final ItemRepository itemRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final BearingRepository bearingRepository;
    private final SealRepository sealRepository;
    private final OilRepository oilRepository;
    private final SparkPlugRepository sparkPlugRepository;
    private final EngineRepository engineRepository;

    private final ItemFactory itemFactory;
    private final ManufacturerFactory manufacturerFactory;
    private final MotorcycleFactory motorcycleFactory;
    private final BearingFactory bearingFactory;
    private final SealFactory sealFactory;
    private final OilFactory oilFactory;
    private final SparkPlugFactory sparkPlugFactory;
    private final EngineFactory engineFactory;
    private final UserRepository userRepository;

    private final ItemService itemService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        populateEmptyTables();
    }

    private void populateEmptyTables() {
        if (repositoryIsEmpty(itemRepository)) {
            createAll(itemFactory, itemRepository);
        }
//        loadManufacturers(repositoryIsEmpty(manufacturerRepository));
//        loadOils(repositoryIsEmpty(oilRepository));
//        loadSparkPlugs(repositoryIsEmpty(sparkPlugRepository));
//        loadBearings(repositoryIsEmpty(bearingRepository));
//        loadSeals(repositoryIsEmpty(sealRepository));
//        loadEngines(repositoryIsEmpty(engineRepository));
//        loadMotorcycles(repositoryIsEmpty(motorcycleRepository));
    }

    private <Entity extends AbstractEntity> Boolean repositoryIsEmpty(final JpaRepository<Entity, Long> repository) {
        return repository.findAll().isEmpty();
    }

    private void loadManufacturers(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(manufacturerFactory, manufacturerRepository);
        }
    }

    private void loadMotorcycles(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(motorcycleFactory, motorcycleRepository);
        }
    }

    private void loadBearings(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(bearingFactory, bearingRepository);
        }
    }

    private void loadSeals(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(sealFactory, sealRepository);
        }
    }

    private void loadOils(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(oilFactory, oilRepository);
        }
    }

    private void loadSparkPlugs(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(sparkPlugFactory, sparkPlugRepository);
        }
    }

    private void loadEngines(final Boolean tableIsEmpty) {
        if (tableIsEmpty) {
            createAll(engineFactory, engineRepository);
        }
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
                itemRepository.save(stubReplacer);
            }
        }
    }



    private List<ItemEntity> collectItems(ItemEntity... items) {
//        List<ItemEntity> collectedItems = new ArrayList<>();
//        for (ItemEntity item : items) {
//            collectedItems.addAll(item.getItemQuantities());
//        }
//        collectedItems.addAll(Arrays.asList(items));
//        return collectedItems;
        return null;
    }

}
















