package com.pazukdev.backend.service;

import com.pazukdev.backend.converter.SealConverter;
import com.pazukdev.backend.dto.product.SealDto;
import com.pazukdev.backend.entity.product.seal.SealEntity;
import com.pazukdev.backend.repository.SealRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
public class SealService extends AbstractService<SealEntity, SealDto>{

    public SealService(final SealRepository repository,
                       final SealConverter converter) {
        super(repository, converter);
    }

    @Override
    public SealEntity findByName(final String name) {
        return ((SealRepository) repository).findByName(name);
    }

    @Transactional
    @Override
    public List<SealEntity> findAll() {
        final List<SealEntity> seals = super.findAll();
        seals.sort(Comparator.comparing(SealEntity::getRotation));
        return seals;
    }

    @Transactional
    public Set<String> getRotations() {
        return new HashSet<>(Arrays.asList("left", "right"));
    }

    @Transactional
    public Set<String> getMaterials() {
        final Set<String> materials = new HashSet<>();
        for (final SealEntity seal : super.findAll()) {
            materials.add(seal.getMaterial());
        }
        return materials;
    }

}
