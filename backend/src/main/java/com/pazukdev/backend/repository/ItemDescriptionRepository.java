package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.item.ItemDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface ItemDescriptionRepository extends JpaRepository<ItemDescriptionEntity, Long> {
}
