package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

    Motorcycle findByName(String name);

}
