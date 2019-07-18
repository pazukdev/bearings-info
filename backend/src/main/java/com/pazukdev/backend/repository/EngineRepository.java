package com.pazukdev.backend.repository;

/**
 * @author Siarhei Sviarkaltsau
 */

import com.pazukdev.backend.entity.product.unit.engine.Engine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineRepository extends JpaRepository<Engine, Long> {

    Engine findByName(final String name);

}
