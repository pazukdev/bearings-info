package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.SparkPlug;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface SparkPlugRepository extends JpaRepository<SparkPlug, Long> {

    SparkPlug findByName(final String name);

}
