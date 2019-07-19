package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.unit.engine.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {

    Engine findByName(final String name);

}
