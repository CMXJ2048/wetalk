package com.wetalk.models;

import jakarta.persistence.*;

@Entity
@Table(name = "channel_members", uniqueConstraints = @UniqueConstraint(columnNames = {"channelId","userId"}))
public class ChannelMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long channelId;
    private Long userId;
    private java.time.Instant joinedAt = java.time.Instant.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getChannelId() { return channelId; }
    public void setChannelId(Long channelId) { this.channelId = channelId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public java.time.Instant getJoinedAt() { return joinedAt; }
    public void setJoinedAt(java.time.Instant joinedAt) { this.joinedAt = joinedAt; }
}
