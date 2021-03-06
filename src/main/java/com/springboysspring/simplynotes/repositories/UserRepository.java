package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.Friendship;
import com.springboysspring.simplynotes.models.FriendshipStatus;
import com.springboysspring.simplynotes.models.User;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String username);

    @NotNull Optional<User> findById(UUID id);

    List<User> findByFirstNameIgnoreCaseOrLastNameIgnoreCaseOrEmailIgnoreCase(String firstName,String lastName,String email);


}
