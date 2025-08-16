package com.wetalk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wetalk.dto.UserDto;
import com.wetalk.models.User;
import com.wetalk.repositories.UserRepository;

/**
 * Business logic for user operations.
 * - Handles registration, retrieval, and update of users.
 * - Encodes passwords before persisting.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** Create a new user from a DTO and persist it. */
    public User registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    /** Retrieve a user by id. */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /** Return all users (consider pagination for production). */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /** Update a user's basic fields and persist changes. */
    public User updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }
}