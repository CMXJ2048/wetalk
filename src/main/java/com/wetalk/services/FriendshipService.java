package com.wetalk.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wetalk.dto.UserPublicDto;
import com.wetalk.models.Friendship;
import com.wetalk.models.User;
import com.wetalk.repositories.FriendshipRepository;
import com.wetalk.repositories.UserRepository;

@Service
public class FriendshipService {
    @Autowired private FriendshipRepository friendshipRepository;
    @Autowired private UserRepository userRepository;

    public void addFriend(Long userId, Long friendId) {
        if (userId.equals(friendId)) throw new RuntimeException("cannot add self");
        if (friendshipRepository.existsByUserIdAndFriendUserId(userId, friendId)) return;
        Friendship f = new Friendship();
        f.setUserId(userId);
        f.setFriendUserId(friendId);
        friendshipRepository.save(f);
    }

    public void removeFriend(Long userId, Long friendId) {
        friendshipRepository.deleteByUserIdAndFriendUserId(userId, friendId);
    }

    public List<UserPublicDto> listFriends(Long userId) {
        List<Long> ids = friendshipRepository.findByUserId(userId).stream().map(Friendship::getFriendUserId).toList();
        return userRepository.findAllById(ids).stream().map(this::toPublic).collect(Collectors.toList());
    }

    private UserPublicDto toPublic(User u){
        UserPublicDto dto = new UserPublicDto();
        dto.setId(u.getId());
        dto.setAccount(u.getAccount());
        dto.setDisplayName(u.getDisplayName());
        dto.setEmail(u.getEmail());
        dto.setAvatarUrl(u.getAvatarUrl());
        return dto;
    }
}
