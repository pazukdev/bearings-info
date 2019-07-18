package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.oil.Oil;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface OilRepository extends JpaRepository<Oil, Long> {

    Oil findByName(final String name);

}
