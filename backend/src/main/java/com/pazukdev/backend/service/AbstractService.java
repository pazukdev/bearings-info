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

    private final JpaRepository<Entity, Long> repository;
    private final EntityDtoConverter<Entity, Dto> converter;

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
        return repository.save(converter.convertToDbo(dto));
    }

    @Transactional
    public void delete(final Long id) throws ProductNotFoundException {
        checkProductExists(id);
        repository.deleteById(id);
    }

    private void checkProductExists(final Long id) throws ProductNotFoundException {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
    }

}
