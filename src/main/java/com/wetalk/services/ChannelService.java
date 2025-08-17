package com.wetalk.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wetalk.models.Channel;
import com.wetalk.models.ChannelMember;
import com.wetalk.repositories.ChannelMemberRepository;
import com.wetalk.repositories.ChannelRepository;

@Service
public class ChannelService {
    @Autowired private ChannelRepository channelRepository;
    @Autowired private ChannelMemberRepository channelMemberRepository;

    public Channel createChannel(String name, Long ownerUserId, List<Long> memberIds){
        Channel c = new Channel();
        c.setName(name);
        c.setOwnerUserId(ownerUserId);
        if (c.getMaxMembers() == null) c.setMaxMembers(100);
        if (memberIds != null && memberIds.size() > c.getMaxMembers()) {
            throw new RuntimeException("member limit exceeded");
        }
        Channel saved = channelRepository.save(c);
        // owner is a member by default
        addMember(saved.getId(), ownerUserId);
        if (memberIds != null) {
            for (Long uid : memberIds) {
                addMember(saved.getId(), uid);
            }
        }
        return saved;
    }

    public void addMember(Long channelId, Long userId){
        Channel ch = channelRepository.findById(channelId).orElseThrow(() -> new RuntimeException("channel not found"));
        int count = channelMemberRepository.countByChannelId(channelId);
        if (count >= (ch.getMaxMembers() == null ? 100 : ch.getMaxMembers())) {
            throw new RuntimeException("member limit reached");
        }
        if (channelMemberRepository.existsByChannelIdAndUserId(channelId, userId)) return;
        ChannelMember m = new ChannelMember();
        m.setChannelId(channelId);
        m.setUserId(userId);
        channelMemberRepository.save(m);
    }

    public void removeMember(Long channelId, Long userId){
        channelMemberRepository.deleteByChannelIdAndUserId(channelId, userId);
    }

    public List<Long> listChannelMembers(Long channelId){
        return channelMemberRepository.findByChannelId(channelId).stream().map(ChannelMember::getUserId).collect(Collectors.toList());
    }

    public List<Channel> listMyChannels(Long userId){
        return channelMemberRepository.findByUserId(userId).stream()
            .map(cm -> cm.getChannelId())
            .distinct()
            .map(id -> channelRepository.findById(id).orElse(null))
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toList());
    }
}
