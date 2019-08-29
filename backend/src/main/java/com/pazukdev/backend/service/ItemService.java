package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.repository.ItemRepository;
import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.SpecificStringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ItemService extends AbstractService<ItemEntity, ItemDto> {

    public ItemService(final ItemRepository repository, final ItemConverter converter) {
        super(repository, converter);
    }

    @Transactional
    public TableDto getItem(final Long id) throws EntityExistsException {
        return createTable(getOne(id));
    }

    public TableDto createTable(final ItemEntity item) {
        final String tableName = SpecificStringUtil.capitalize(item.getCategory()) + " " + item.getName();
        final List<String[]> list = new ArrayList<>();
        list.add(new String[]{"Name", item.getName()});
        list.add(new String[]{"Quantity", item.getQuantity() != null ? item.getQuantity().toString() : "0"});
        for (Map.Entry entry : ItemUtil.toMap(item.getDescription()).entrySet()) {
            list.add(new String[]{entry.getKey().toString(), entry.getValue().toString()});
        }

        final String[] replacers = item.getReplacer().replaceAll(" ", "").split(";");
        int i = 0;
        for (String replacer : replacers) {
            list.add(new String[]{"Replacer #" + ++i, replacer});
        }
        int j = 0;
        final String[][] matrix = new String[list.size()][];
        for (String[] s : list) {
            matrix[j++] = s;
        }
        return new TableDto(tableName, matrix);
    }

    @Transactional
    @Override
    public ItemEntity findByName(final String name) {
        return ((ItemRepository) repository).findByName(name);
    }

    @Transactional
    public List<ItemEntity> find(final String category, final String... names) {
        final List<ItemEntity> items = new ArrayList<>();
        for (final String name : names) {
            items.add(find(category, name));
        }
        return items;
    }

    @Transactional
    public ItemEntity find(final String category, final String name) {
        for (final ItemEntity item : find(category)) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Transactional
    public List<ItemEntity> find(final String category) {
        final List<ItemEntity> categorizedItems = new ArrayList<>();
        for (final ItemEntity item : findAll()) {
            if (item.getCategory().equals(category)) {
                categorizedItems.add(item);
            }
        }
        return categorizedItems;
    }

}




















