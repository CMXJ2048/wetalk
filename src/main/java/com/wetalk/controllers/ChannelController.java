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

import com.wetalk.models.Channel;
import com.wetalk.services.ChannelService;
import com.wetalk.utils.AuthUtils;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    @Autowired private ChannelService channelService;

    @PostMapping
    public ResponseEntity<Channel> create(@RequestBody java.util.Map<String, Object> body){
        String name = (String) body.getOrDefault("name", "");
        Long ownerUserId = AuthUtils.currentUserId();
    if (ownerUserId == null) return ResponseEntity.status(401).build();
        @SuppressWarnings("unchecked")
        List<Number> mids = (List<Number>) body.get("memberIds");
        List<Long> memberIds = mids == null ? java.util.List.of() : mids.stream().map(Number::longValue).toList();
        Channel c = channelService.createChannel(name, ownerUserId, memberIds);
        return ResponseEntity.status(201).body(c);
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<Void> addMember(@PathVariable Long id, @RequestBody java.util.Map<String, Long> body){
        Long userId = body.get("userId");
        channelService.addMember(id, userId);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long id, @PathVariable Long userId){
        channelService.removeMember(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Channel>> myChannels(){
        Long userId = AuthUtils.currentUserId();
    if (userId == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(channelService.listMyChannels(userId));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<Long>> members(@PathVariable Long id){
        return ResponseEntity.ok(channelService.listChannelMembers(id));
    }
}
