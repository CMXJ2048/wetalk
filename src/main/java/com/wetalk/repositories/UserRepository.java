package com.wetalk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wetalk.models.User;

/**
 * Spring Data JPA repository for User entity, providing CRUD and query methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /** Find a user by unique account. */
    User findByAccount(String account);
    /** Find a user by unique email. */
    User findByEmail(String email);
    /** Search by account or displayName (case-insensitive), limit top 20. */
    java.util.List<User> findTop20ByAccountContainingIgnoreCaseOrDisplayNameContainingIgnoreCase(String account, String displayName);
}