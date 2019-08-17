package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
