package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@RequiredArgsConstructor
public abstract class AbstractService<Entity extends AbstractEntity, Dto extends AbstractDto> {

    protected final JpaRepository<Entity, Long> repository;
    protected final EntityDtoConverter<Entity, Dto> converter;

    @Transactional
    public List<Entity> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Entity getOne(final Long id) throws ProductNotFoundException {
        checkProductExists(id);
        return repository.getOne(id);
    }

    @Transactional
    public Entity create(final Dto dto) {
        return repository.save(converter.convertToEntity(dto));
    }

    @Transactional
    public Entity delete(final Long id) throws ProductNotFoundException {
        final Entity entity = getOne(id);
        repository.deleteById(entity.getId());
        return entity;
    }

    @Transactional
    public List<Entity> deleteAll(final List<Long> ids) throws ProductNotFoundException {
        final List<Entity> entities = new ArrayList<>();
        for (final Long id : ids) {
            entities.add(getOne(id));
            repository.deleteById(id);
        }
        return entities;
    }

    public Boolean productExists(final Long id) throws ProductNotFoundException {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        return true;
    }

    private void checkProductExists(final Long id) throws ProductNotFoundException {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
    }

    @Transactional
    public Entity search(final DefaultSearchRequest request) {
        return findByName(request);
    }

    @Transactional
    public List<Entity> search(final List<Long> ids) {
        final List<Entity> entities;
        if (ids == null || ids.isEmpty()) {
            entities = repository.findAll();
        } else {
            entities = repository.findAllById(ids);
        }
        return entities;
    }

    protected abstract Entity findByName(final DefaultSearchRequest request);

}
















