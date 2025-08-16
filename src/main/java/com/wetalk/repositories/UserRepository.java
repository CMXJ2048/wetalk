package com.wetalk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wetalk.models.User;

/**
 * Spring Data JPA repository for User entity, providing CRUD and query methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /** Find a user by unique username. */
    User findByUsername(String username);
}