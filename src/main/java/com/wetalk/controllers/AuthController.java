package com.wetalk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wetalk.dto.UserDto;
import com.wetalk.models.User;
import com.wetalk.services.UserService;

/**
 * Authentication endpoints: registration and login.
 * - POST /api/auth/register: create a new user account.
 * - POST /api/auth/login: placeholder for JWT login (to be implemented).
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user.
     * Request body: { username, email, password }
     * Response: persisted User entity (temporary; later replace with DTO)
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto) {
        User saved = userService.registerUser(userDto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Login placeholder. Will return JWT token after implementing authentication.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        // Placeholder for future JWT login implementation
        return ResponseEntity.ok("login not implemented");
    }
}