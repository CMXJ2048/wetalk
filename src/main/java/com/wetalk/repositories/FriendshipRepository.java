package com.wetalk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wetalk.models.Friendship;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUserId(Long userId);
    boolean existsByUserIdAndFriendUserId(Long userId, Long friendUserId);
    void deleteByUserIdAndFriendUserId(Long userId, Long friendUserId);
}
