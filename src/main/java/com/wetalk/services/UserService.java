package com.wetalk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wetalk.dto.UserDto;
import com.wetalk.dto.UserPublicDto;
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
    user.setAccount(userDto.getAccount());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
    user.setDisplayName(userDto.getDisplayName());
    user.setAvatarUrl(userDto.getAvatarUrl());
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

    /** Return public DTOs of all users (trimmed fields). */
    public List<UserPublicDto> listUsersPublic() {
        return userRepository.findAll().stream().map(this::toPublicDto).collect(Collectors.toList());
    }

    /** Keyword search for adding friends (by account/displayName). */
    public List<UserPublicDto> searchUsers(String keyword) {
        List<User> list = userRepository.findTop20ByAccountContainingIgnoreCaseOrDisplayNameContainingIgnoreCase(keyword, keyword);
        return list.stream().map(this::toPublicDto).collect(Collectors.toList());
    }

    private UserPublicDto toPublicDto(User u) {
        UserPublicDto dto = new UserPublicDto();
        dto.setId(u.getId());
        dto.setAccount(u.getAccount());
        dto.setDisplayName(u.getDisplayName());
        dto.setEmail(u.getEmail());
        dto.setAvatarUrl(u.getAvatarUrl());
        return dto;
    }

    /** Update a user's basic fields and persist changes. */
    public User updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getDisplayName() != null) user.setDisplayName(userDto.getDisplayName());
        if (userDto.getAvatarUrl() != null) user.setAvatarUrl(userDto.getAvatarUrl());
        return userRepository.save(user);
    }

    /** Check if account/email is available. */
    public boolean isAccountAvailable(String account) {
        return userRepository.findByAccount(account) == null;
    }
    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email) == null;
    }

    /** Change password with old password verification. */
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}