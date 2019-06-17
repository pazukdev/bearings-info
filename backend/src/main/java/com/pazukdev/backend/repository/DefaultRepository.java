package com.pazukdev.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface DefaultRepository<Entity> extends JpaRepository<Entity, Long> {

    Entity findByName(String name);

}
