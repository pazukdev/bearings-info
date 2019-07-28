package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.oil.OilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface OilRepository extends JpaRepository<OilEntity, Long> {

    OilEntity findByName(final String name);

}
