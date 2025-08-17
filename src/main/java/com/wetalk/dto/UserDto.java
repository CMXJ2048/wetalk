package com.wetalk.dto;

/**
 * Data Transfer Object for user input/output at API boundaries.
 * In production, prefer returning DTOs instead of entities.
 */
public class UserDto {
    /** User identifier (optional in responses). */
    private Long id;
    /** User-defined unique account (login name). */
    private String account;
    /** Email address (unique). */
    private String email;
    /** Plain password in requests (will be encoded before save). */
    private String password;
    /** Display name (not unique). */
    private String displayName;
    /** Avatar URL (upload result or picture_base selection). */
    private String avatarUrl;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}