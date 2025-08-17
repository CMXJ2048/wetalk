package com.wetalk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA entity representing application user accounts.
 * Table name is "users". Passwords are stored as BCrypt hashes.
 */
@Entity
@Table(name = "users")
public class User {
    /** Primary key (auto-increment). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** User-defined unique account (login name). */
    @Column(unique = true)
    private String account;

    /** BCrypt-hashed password (never store plain text). */
    @JsonIgnore
    private String password;

    /** User email address (unique). */
    @Column(unique = true)
    private String email;

    /** Display name (not unique), shown publicly. */
    private String displayName;

    /** Avatar URL, can be uploaded or from picture_base. */
    private String avatarUrl;

    // Constructors
    public User() {}

    public User(Long id, String account, String password, String email, String displayName, String avatarUrl) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.email = email;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}