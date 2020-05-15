package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.Friendship;
import com.springboysspring.simplynotes.models.Friendship.FriendshipId;
import com.springboysspring.simplynotes.models.FriendshipStatus;
import com.springboysspring.simplynotes.models.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, UUID> {

     List<Friendship> findAllByOwnerId(UUID owner_id);

     List<Friendship> findAllByOwnerAndFriendshipStatus(User owner, FriendshipStatus friendshipStatus);

}
