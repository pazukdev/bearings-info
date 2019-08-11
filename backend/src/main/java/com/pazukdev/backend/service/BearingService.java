package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import com.pazukdev.backend.product.specification.context.Spec;
import com.pazukdev.backend.repository.BearingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class BearingService extends AbstractService<BearingEntity, BearingDto>{

    private final Spec spec;

    public BearingService(final BearingRepository repository,
                          final BearingConverter converter,
                          final Spec spec) {
        super(repository, converter);
        this.spec = spec;
    }

    @Transactional
    @Override
    public List<BearingEntity> findAll() {
        final List<BearingEntity> bearings = super.findAll();
        bearings.sort(Comparator.comparing(BearingEntity::getType));
        return bearings;
    }

    @Transactional
    @Override
    public BearingEntity findByName(final String name) {
        return ((BearingRepository) repository).findByName(name);
    }

    public List<String> getTypes() {
        return spec.getSpecs("type");
    }

}













