package com.wetalk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wetalk.models.ChannelMember;

@Repository
public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {
    int countByChannelId(Long channelId);
    boolean existsByChannelIdAndUserId(Long channelId, Long userId);
    List<ChannelMember> findByChannelId(Long channelId);
    List<ChannelMember> findByUserId(Long userId);
    void deleteByChannelIdAndUserId(Long channelId, Long userId);
}
