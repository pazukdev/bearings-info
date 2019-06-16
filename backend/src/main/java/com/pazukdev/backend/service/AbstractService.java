package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.abstraction.EntityDtoConverter;
import com.pazukdev.backend.dto.abstraction.AbstractDto;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

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
    public Entity delete(final Long id) throws ProductNotFoundException {
        final Entity entity = converter.convertToEntity(get(id));
        repository.deleteById(entity.getId());
        return entity;
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

}
