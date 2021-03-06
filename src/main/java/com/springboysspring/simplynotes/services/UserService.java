package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.Friendship;
import com.springboysspring.simplynotes.models.FriendshipStatus;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.FriendshipRepository;
import com.springboysspring.simplynotes.repositories.UserRepository;
import com.springboysspring.simplynotes.services.handlers.FriendshipHandler;
import com.springboysspring.simplynotes.services.handlers.UserHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserHandler userHandler;
    private final FriendshipRepository friendshipRepository;
    private final FriendshipHandler friendshipHandler;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
        UserHandler userHandler, FriendshipRepository friendshipRepository,
        FriendshipHandler friendshipHandler) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userHandler = userHandler;
        this.friendshipRepository = friendshipRepository;
        this.friendshipHandler = friendshipHandler;
    }

    public void register(User user) {
        Optional<User> isEmailTaken = userRepository.findByEmail(user.getEmail());
        userHandler.checkBooleanOrElseThrow(isEmailTaken.isPresent(),
            String.format("Email %s is already taken!", user.getEmail()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    public List<User> searchUsers(String firstName, String lastName, String email) {
        return userRepository.
            findByFirstNameIgnoreCaseOrLastNameIgnoreCaseOrEmailIgnoreCase(firstName, lastName, email);
    }

    public void addFriend(UUID userId, UUID friendId) {
        userHandler.invoke(userId, friendId, User::addFriend);
    }

    public void deleteFriend(UUID userId, UUID friendId) {
        userHandler.invoke(userId, friendId, User::deleteFriend);
    }

    public String changeFriendshipStatus(UUID userId, UUID friendId, FriendshipStatus status) {
        userHandler.verifyInputtedId(userId);
        userHandler.verifyInputtedId(friendId);

        switch (status) {
            case ACCEPTED -> {
                changeStatus(userId, friendId, Friendship::acceptFriendRequest);
                return "Friend request accepted successfully!";
            }
            case DECLINED -> {
                changeStatus(userId, friendId, Friendship::declineFriendRequest);
                return "Friend request declined successfully!";
            }
            default -> throw new APIRequestException(
                String.format("%s is"
                    + " not an alternative! Please choose ACCEPTED or DECLINED.", status));
        }
    }

    private void changeStatus(UUID userId, UUID friendId, BiConsumer<Friendship, Friendship> changeFriendshipStatus) {
        friendshipHandler.invoke(userId, friendId, changeFriendshipStatus);
    }

    public List<Friendship> getFriendsByStatus(UUID userId, FriendshipStatus friendshipStatus) {
        User currentUser = userHandler.verifyInputtedId(userId);
        userHandler.checkForAuthentication(currentUser);
        return new ArrayList<>(friendshipRepository.findAllByOwnerAndFriendshipStatus(currentUser, friendshipStatus));
    }

    public List<Friendship> getAllFriends(UUID userId) {
        User currentUser = userHandler.verifyInputtedId(userId);
        userHandler.checkForAuthentication(currentUser);
        return new ArrayList<>(friendshipRepository.findAllByOwner(currentUser));
    }

    public User getUser(UUID id) {
        return userHandler.verifyInputtedId(id);
    }
}
