package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.search.DefaultSearchRequest;
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
    public List<Dto> getProductsList() {
        return converter.convertToDtoList(repository.findAll());
    }

    @Transactional
    public Dto get(final Long id) throws ProductNotFoundException {
        checkProductExists(id);
        return converter.convertToDto(repository.getOne(id));
    }

    @Transactional
    public Entity create(final Dto dto) {
        return repository.save(converter.convertToEntity(dto));
    }

    @Transactional
    public Dto delete(final Long id) throws ProductNotFoundException {
        final Entity entity = converter.convertToEntity(get(id));
        repository.deleteById(entity.getId());
        return converter.convertToDto(entity);
    }

    @Transactional
    public List<Dto> deleteAll(final List<Long> ids) throws ProductNotFoundException {
        final List<Dto> dtos = new ArrayList<>();
        for (final Long id : ids) {
            dtos.add(get(id));
            repository.deleteById(id);
        }
        return dtos;
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
    public Dto search(final DefaultSearchRequest request) {
        final Entity entity = findByName(request);
        return converter.convertToDto(entity);
    }

    @Transactional
    public List<Dto> search(final List<Long> ids) {
        List<Entity> entities = repository.findAllById(ids);
        return converter.convertToDtoList(entities);
    }

    protected abstract Entity findByName(final DefaultSearchRequest request);

}
















