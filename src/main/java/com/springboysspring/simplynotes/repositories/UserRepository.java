package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String username);

    Optional<User> findById(UUID id);

}
