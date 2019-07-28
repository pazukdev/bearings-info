package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface BearingRepository extends JpaRepository<BearingEntity, Long> {

    BearingEntity findByName(final String name);

}
