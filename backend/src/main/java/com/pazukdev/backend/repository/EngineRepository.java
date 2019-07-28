package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.unit.engine.EngineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface EngineRepository extends JpaRepository<EngineEntity, Long> {

    EngineEntity findByName(final String name);

}
