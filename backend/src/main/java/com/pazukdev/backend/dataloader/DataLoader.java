package com.pazukdev.backend.dataloader;

import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.entity.AbstractEntityFactory;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.entity.item.ItemFactory;
import com.pazukdev.backend.entity.item.ItemQuantity;
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
import com.pazukdev.backend.util.SpecificStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final ItemRepository itemRepository;
    private final ItemQuantityRepository itemQuantityRepository;
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
        loadManufacturers(repositoryIsEmpty(manufacturerRepository));
        loadOils(repositoryIsEmpty(oilRepository));
        loadSparkPlugs(repositoryIsEmpty(sparkPlugRepository));
        loadBearings(repositoryIsEmpty(bearingRepository));
        loadSeals(repositoryIsEmpty(sealRepository));
        loadEngines(repositoryIsEmpty(engineRepository));
        loadMotorcycles(repositoryIsEmpty(motorcycleRepository));
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
            Entity entityToSave = entity;
            if (entity instanceof ItemEntity) {
                ItemEntity item = (ItemEntity) entity;

                for (final Map.Entry entry : ItemUtil.toMap(item.getDescription()).entrySet()) {
                    if (entry.getValue().toString().contains(";")) {
                        final String[] names = entry.getValue().toString().replaceAll(" ", "").split(";");
                        for (final String name : names) {
                            addChildItem(item, name, entry.getKey().toString());
                        }
                    } else {
                        addChildItem(item, entry.getValue().toString(), entry.getKey().toString());
                    }
                }

                entityToSave = (Entity) item;
            }

            repository.save(entityToSave);
        }
    }

    private void addChildItem(final ItemEntity parentItem, final String value, final String category) {
        String name;
        Integer quantity;
        if (SpecificStringUtil.containsParentheses(value)) {
            name = SpecificStringUtil.getStringBeforeParentheses(value);
            quantity = SpecificStringUtil.extractIntegerAutomatically(value);
        } else {
            name = value;
            quantity = 1;
        }
        final ItemEntity child = itemService.find(category, name);
        if (child != null) {
            final ItemQuantity itemQuantity = new ItemQuantity();
            itemQuantity.setName(parentItem.getName() + "-" + child.getName());
            itemQuantity.setItem(child);
            itemQuantity.setQuantity(quantity);
            itemQuantityRepository.save(itemQuantity);
            parentItem.getItemQuantities().add(itemQuantity);
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
















