package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

        ItemImg findByName(final String name);

}
