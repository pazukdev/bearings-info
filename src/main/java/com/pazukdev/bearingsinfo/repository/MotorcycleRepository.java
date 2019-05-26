package com.pazukdev.bearingsinfo.repository;

import com.pazukdev.bearingsinfo.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
}
