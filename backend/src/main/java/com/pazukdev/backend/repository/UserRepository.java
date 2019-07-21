package com.pazukdev.backend.repository;

import com.pazukdev.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Siarhei Sviarkaltsau
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(final String name);

}
