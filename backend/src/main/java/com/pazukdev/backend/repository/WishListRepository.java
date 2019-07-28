package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Long> {

    WishListEntity findByName(final String name);

}
