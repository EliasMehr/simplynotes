package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
