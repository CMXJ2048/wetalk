package com.wetalk.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wetalk.dto.UserDto;
import com.wetalk.models.User;
import com.wetalk.services.UserService;

/**
 * User-related REST endpoints for profile retrieval and update.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * Create user (register).
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User created = userService.registerUser(userDto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Get a user by id.
     * Path: /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * List all users. For MVP only; add pagination and DTO mapping later.
     */
    @GetMapping
    public ResponseEntity<List<com.wetalk.dto.UserPublicDto>> getAllUsers(
        @RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            return ResponseEntity.ok(userService.searchUsers(keyword));
        }
        return ResponseEntity.ok(userService.listUsersPublic());
    }

    /**
     * Update a user's basic info.
     * Body: { username, email }
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Change password.
     * PUT /api/users/{id}/password
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        String oldPassword = body.getOrDefault("oldPassword", "");
        String newPassword = body.getOrDefault("newPassword", "");
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

    /**
     * Availability check: account or email.
     * GET /api/users/availability?account=xxx or ?email=yyy
     */
    @GetMapping("/availability")
    public ResponseEntity<java.util.Map<String, Boolean>> availability(
        @RequestParam(required = false) String account,
        @RequestParam(required = false) String email) {
        boolean available;
        if (account != null) available = userService.isAccountAvailable(account);
        else if (email != null) available = userService.isEmailAvailable(email);
        else available = false;
        return ResponseEntity.ok(java.util.Collections.singletonMap("available", available));
    }

    /**
     * Upload avatar file for a user (multipart/form-data: file)
     * Saves to local folder uploads/avatars/YYYY/MM and updates user's avatarUrl with a public path.
     */
    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadAvatar(
        @PathVariable Long id,
        @RequestPart("file") MultipartFile file
    ) throws java.io.IOException {
        // Validate content type and size
        if (file.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error", "file is empty"));
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/webp"))) {
            return ResponseEntity.badRequest().body(Map.of("error", "unsupported file type"));
        }
        long maxSize = 2L * 1024 * 1024; // 2MB
        if (file.getSize() > maxSize) {
            return ResponseEntity.badRequest().body(Map.of("error", "file too large"));
        }

        // Build storage path
        java.time.LocalDate now = java.time.LocalDate.now();
        String baseDir = System.getProperty("user.dir") + java.io.File.separator + "uploads" + java.io.File.separator + "avatars";
        String subDir = now.getYear() + java.io.File.separator + String.format("%02d", now.getMonthValue());
        java.nio.file.Path dir = java.nio.file.Paths.get(baseDir, subDir);
        java.nio.file.Files.createDirectories(dir);

        // Normalize extension
        String ext = switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> "";
        };
        String filename = UUID.randomUUID().toString().replace("-", "") + ext;
        java.nio.file.Path target = dir.resolve(filename).normalize();

        // Write file
        file.transferTo(target.toFile());

        // Public URL path served by WebMvc static mapping
        String publicPath = "/uploads/avatars/" + now.getYear() + "/" + String.format("%02d", now.getMonthValue()) + "/" + filename;

    // Update user avatarUrl
    userService.updateUser(id, toDtoWithAvatar(publicPath));

        return ResponseEntity.ok(Map.of("avatarUrl", publicPath));
    }

    private UserDto toDtoWithAvatar(String avatarUrl) {
        UserDto dto = new UserDto();
        dto.setAvatarUrl(avatarUrl);
        return dto;
    }
}