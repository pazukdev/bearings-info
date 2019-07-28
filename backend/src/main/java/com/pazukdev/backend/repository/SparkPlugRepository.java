package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.sparkplug.SparkPlugEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface SparkPlugRepository extends JpaRepository<SparkPlugEntity, Long> {

    SparkPlugEntity findByName(final String name);

}
