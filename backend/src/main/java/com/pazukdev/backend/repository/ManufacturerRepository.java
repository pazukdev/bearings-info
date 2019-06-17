package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Manufacturer findByName(String name);

}
