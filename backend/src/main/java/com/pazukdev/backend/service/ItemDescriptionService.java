package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.ItemDescriptionConverter;
import com.pazukdev.backend.dto.item.ItemDescriptionDto;
import com.pazukdev.backend.entity.item.ItemDescriptionEntity;
import com.pazukdev.backend.repository.ItemDescriptionRepository;
import org.springframework.stereotype.Service;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class ItemDescriptionService extends AbstractService<ItemDescriptionEntity, ItemDescriptionDto> {

    public ItemDescriptionService(final ItemDescriptionRepository repository,
                                  final ItemDescriptionConverter converter) {
        super(repository, converter);
    }

    @Override
    public ItemDescriptionEntity findByName(String name) {
        return null;
    }
}
