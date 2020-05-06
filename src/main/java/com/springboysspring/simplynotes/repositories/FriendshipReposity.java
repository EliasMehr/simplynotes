package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendshipReposity extends JpaRepository<Friendship, UUID> {
}
