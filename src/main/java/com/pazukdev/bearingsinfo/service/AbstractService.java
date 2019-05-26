package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.converter.abstraction.EntityDtoConverter;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.entity.AbstractEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Siarhei Sviarkaltsau
 */
@RequiredArgsConstructor
public abstract class AbstractService<Entity extends AbstractEntity, Dto extends AbstractDto> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

    private final JpaRepository<Entity, Long> repository;
    private final EntityDtoConverter<Entity, Dto> converter;
    private final AbstractDtoFactory<Dto> factory;

    public void createProduct(final Dto dto) {
        repository.save(converter.convertToDbo(dto));
    }

    public List<Dto> getProductsList() {
        List<Entity> productsList = repository.findAll();
        if (productsList.isEmpty()) {
            createDefaultProducts();
            productsList = repository.findAll();
        }

        return convertToDtoList(productsList);
    }

    private List<Dto> convertToDtoList(final List<Entity> bearingList) {
        return bearingList.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    private void createDefaultProducts() {
        for (final Dto dto : factory.createFromDataFile()) {
            createProduct(dto);
        }
    }

}
