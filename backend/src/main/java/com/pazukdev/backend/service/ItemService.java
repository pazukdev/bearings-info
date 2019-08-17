package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemConverter;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.entity.item.ItemEntity;
import com.pazukdev.backend.repository.ItemRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ItemService extends AbstractService<ItemEntity, ItemDto> {

    public ItemService(final ItemRepository repository, final ItemConverter converter) {
        super(repository, converter);
    }

    @Override
    public ItemEntity findByName(String name) {
        return null;
    }
}
