package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.product.seal.SealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface SealRepository extends JpaRepository<SealEntity, Long> {

    SealEntity findByName(String name);

}
