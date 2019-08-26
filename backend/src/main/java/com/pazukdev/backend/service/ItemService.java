package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.repository.ItemRepository;
import com.pazukdev.backend.util.SpecificStringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

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
        final String[][] matrix = {{"Name", item.getName()}};
        return new TableDto(tableName, matrix);
    }

    @Transactional
    @Override
    public ItemEntity findByName(String name) {
        return null;
    }
}
