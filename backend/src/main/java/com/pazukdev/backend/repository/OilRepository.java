package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.oil.Oil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface OilRepository extends JpaRepository<Oil, Long> {

    Oil findByName(final String name);

}
