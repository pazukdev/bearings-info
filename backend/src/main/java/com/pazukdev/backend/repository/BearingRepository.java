package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.Bearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface BearingRepository extends JpaRepository<Bearing, Long> {

    Bearing findByName(final String name);

}
