package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByName(final String name);

    Item findFirstByCategory(final String category);

    Item findFirstByCategoryAndName(final String category, final String name);

    Item findFirstByCategoryAndNameAndStatus(final String category, final String name, final String status);

}
