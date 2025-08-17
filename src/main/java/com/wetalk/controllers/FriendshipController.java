package com.wetalk.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wetalk.dto.UserPublicDto;
import com.wetalk.utils.AuthUtils;
import com.wetalk.services.FriendshipService;

@RestController
@RequestMapping("/api/friends")
public class FriendshipController {

    @Autowired private FriendshipService friendshipService;

    @PostMapping
    public ResponseEntity<Void> addFriend(@RequestBody java.util.Map<String, Long> body){
        Long userId = AuthUtils.currentUserId();
    if (userId == null) return ResponseEntity.status(401).build();
        Long friendId = body.get("friendId");
        friendshipService.addFriend(userId, friendId);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long friendId){
        Long userId = AuthUtils.currentUserId();
    if (userId == null) return ResponseEntity.status(401).build();
        friendshipService.removeFriend(userId, friendId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserPublicDto>> listFriends(){
        Long userId = AuthUtils.currentUserId();
    if (userId == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(friendshipService.listFriends(userId));
    }
}
