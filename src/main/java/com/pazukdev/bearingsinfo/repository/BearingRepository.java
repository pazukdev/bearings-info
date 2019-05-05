package com.pazukdev.bearingsinfo.repository;

import com.pazukdev.bearingsinfo.dbo.Bearing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface BearingRepository extends JpaRepository<Bearing, Long> {}
