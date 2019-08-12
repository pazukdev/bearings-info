package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import com.pazukdev.backend.repository.BearingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class BearingService extends AbstractService<BearingEntity, BearingDto> {

    public BearingService(final BearingRepository repository,
                          final BearingConverter converter) {
        super(repository, converter);
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

    @Transactional
    public Set<String> getTypes() {
        final Set<String> types = new HashSet<>();
        for (final BearingEntity bearing : super.findAll()) {
            types.add(bearing.getType());
        }
        return types;
    }

    @Transactional
    public Set<String> getRollingElements() {
        final Set<String> rollingElements = new HashSet<>();
        for (final BearingEntity bearing : super.findAll()) {
            rollingElements.add(bearing.getRollingElement());
        }
        return rollingElements;
    }

}













