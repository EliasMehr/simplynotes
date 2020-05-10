package com.springboysspring.simplynotes.repositories;

import java.util.Optional;
import java.util.UUID;

import com.springboysspring.simplynotes.models.Role;
import com.springboysspring.simplynotes.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByType(Type type);
}