package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.converter.abstraction.EntityDtoConverter;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.entity.AbstractEntity;
import com.pazukdev.bearingsinfo.exception.ProductNotExistException;
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
    private final AbstractDtoFactory<Dto> factory;

    @Transactional
    public List<Dto> getProductsList() {
        List<Entity> productsList = repository.findAll();
        if (productsList.isEmpty()) {
            createDefaultProducts();
            productsList = repository.findAll();
        }

        return converter.convertToDtoList(productsList);
    }

    @Transactional
    public Dto get(final Long id) throws ProductNotExistException {
        checkProductExists(id);
        return converter.convertToDto(repository.getOne(id));
    }

    @Transactional
    public Entity create(final Dto dto) {
        return repository.save(converter.convertToDbo(dto));
    }

    @Transactional
    public void delete(final Long id) throws ProductNotExistException {
        checkProductExists(id);
        repository.deleteById(id);
    }

    private void checkProductExists(final Long id) throws ProductNotExistException {
        if (!repository.existsById(id)) {
            throw new ProductNotExistException(id);
        }
    }

    private void createDefaultProducts() {
        for (final Dto dto : factory.createDtosFromCSVFile()) {
            create(dto);
        }
    }

}
