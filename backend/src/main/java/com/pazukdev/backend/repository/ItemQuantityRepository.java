package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.item.ItemQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface ItemQuantityRepository extends JpaRepository<ItemQuantity, Long> {

    ItemQuantity findByName(final String name);

}
