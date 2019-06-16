package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    //@Query("SELECT t FROM manufacturer t WHERE t.name = ?1")
    Manufacturer findByName(String name);

}
