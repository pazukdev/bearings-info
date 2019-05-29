package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
}
