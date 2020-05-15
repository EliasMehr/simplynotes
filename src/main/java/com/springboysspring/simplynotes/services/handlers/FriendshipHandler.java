package com.springboysspring.simplynotes.services.handlers;

import static com.springboysspring.simplynotes.models.FriendshipStatus.DECLINED;
import static com.springboysspring.simplynotes.models.FriendshipStatus.PENDING;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.Friendship;
import com.springboysspring.simplynotes.repositories.FriendshipRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class FriendshipHandler {

    @Transactional
    public void invoke(
        UUID userId,
        UUID friendId,
        FriendshipRepository friendshipRepository,
        BiConsumer<Friendship, Friendship> changeFriendshipStatus
    ) {

        Optional<Friendship> ownerFriendship = friendshipRepository.findByOwnerIdAndFriendId(userId, friendId);
        Optional<Friendship> friendFriendship = friendshipRepository.findByOwnerIdAndFriendId(friendId, userId);

        if (friendFriendship.isPresent() && ownerFriendship.isPresent()) {
            Friendship friendsFriendship = friendFriendship.get();
            Friendship currentUserFriendship = ownerFriendship.get();

            if (friendsFriendship.getFriendshipStatus() == PENDING || friendsFriendship.getFriendshipStatus()== DECLINED) {
                changeFriendshipStatus.accept(currentUserFriendship, friendsFriendship);
                friendshipRepository.save(friendsFriendship);
            } else {
                // User may have declined other users request or they may be already friends!
                throw new APIRequestException("Permission denied! Users have already sent friend request to each other!");
            }
        } else {
            throw new APIRequestException("Users have not a pending request");
        }
    }
}
