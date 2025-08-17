package com.wetalk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wetalk.dto.UserDto;
import com.wetalk.models.User;
import com.wetalk.repositories.UserRepository;
import com.wetalk.services.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired private UserRepository userRepository;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;

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
    public ResponseEntity<java.util.Map<String, Object>> login(@RequestBody UserDto userDto) {
        // Simple login by account + password
        User u = userRepository.findByAccount(userDto.getAccount());
        if (u == null) {
            return ResponseEntity.status(401).body(java.util.Map.of("error", "Invalid credentials"));
        }
        // Password verification
    if (!passwordEncoder.matches(userDto.getPassword(), u.getPassword())) {
            return ResponseEntity.status(401).body(java.util.Map.of("error", "Invalid credentials"));
        }
        String token = jwtService.generateToken(u.getId(), u.getAccount());
        return ResponseEntity.ok(java.util.Map.of("token", token, "userId", u.getId(), "account", u.getAccount()));
    }
}