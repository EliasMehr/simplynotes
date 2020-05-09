package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FriendshipReposity extends JpaRepository<Friendship, UUID> {
}
