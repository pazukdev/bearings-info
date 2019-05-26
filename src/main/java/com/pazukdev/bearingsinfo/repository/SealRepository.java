package com.pazukdev.bearingsinfo.repository;

import com.pazukdev.bearingsinfo.entity.Seal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface SealRepository extends JpaRepository<Seal, Long> {
}
