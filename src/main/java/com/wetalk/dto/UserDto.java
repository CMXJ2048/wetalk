package com.wetalk.dto;

/**
 * Data Transfer Object for user input/output at API boundaries.
 * In production, prefer returning DTOs instead of entities.
 */
public class UserDto {
    /** User identifier (optional in create requests). */
    private Long id;
    /** Unique username. */
    private String username;
    /** Email address. */
    private String email;
    /** Plain password in requests (will be encoded before save). */
    private String password;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}