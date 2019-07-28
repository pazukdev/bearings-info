package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface MotorcycleRepository extends JpaRepository<MotorcycleEntity, Long> {

    MotorcycleEntity findByName(final String name);

}
