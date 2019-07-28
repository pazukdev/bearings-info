package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {

    ManufacturerEntity findByName(final String name);

}
