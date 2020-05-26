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

     Optional<Friendship> findByOwnerIdAndFriendId(UUID owner_id, UUID friend_id);
     Optional<Friendship> findByFriendIdAndOwnerId(UUID friend_id, UUID owner_id);


     List<Friendship> findAllByOwnerAndFriendshipStatus(User owner, FriendshipStatus friendshipStatus);
     List<Friendship> findAllByOwner(User owner);

}
