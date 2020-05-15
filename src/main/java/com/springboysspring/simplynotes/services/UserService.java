package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.models.Friendship;
import com.springboysspring.simplynotes.models.FriendshipStatus;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.FriendshipRepository;
import com.springboysspring.simplynotes.repositories.UserRepository;
import com.springboysspring.simplynotes.services.handlers.UserHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticatedUserEmail authenticatedUserEmail;

    private final UserHandler userHandler;
    private final FriendshipRepository friendshipRepository;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
        AuthenticatedUserEmail authenticatedUserEmail,
        UserHandler userHandler, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticatedUserEmail = authenticatedUserEmail;
        this.userHandler = userHandler;
        this.friendshipRepository = friendshipRepository;
    }

    public void register(User user) {
        Optional<User> isEmailTaken = userRepository.findByEmail(user.getEmail());
        userHandler.checkUserInput(
            isEmailTaken.isPresent(),
            String.format("Email %s is already taken!", user.getEmail()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);

    }

    public List<User> searchUsers(String firstName, String lastName, String email) {
        return userRepository.
            findByFirstNameIgnoreCaseOrLastNameIgnoreCaseOrEmailIgnoreCase(
                firstName,
                lastName,
                email);
    }

    public void addFriend(UUID userId, UUID friendId) {
        userHandler
            .invoke(
                userId,
                friendId,
                authenticatedUserEmail.getAuthenticatedUserEmail(),
                User::addFriend,
                userRepository);
    }

    public void deleteFriend(UUID userId, UUID friendId) {
        userHandler
            .invoke(
                userId,
                friendId,
                authenticatedUserEmail.getAuthenticatedUserEmail(),
                User::deleteFriend,
                userRepository);
    }

    public List<Friendship> getFriendsByStatus( UUID userId,FriendshipStatus friendshipStatus) {

        Optional<User> optionalUser = userRepository.findById(userId);
        userHandler.checkUserInput(
            optionalUser.isEmpty(),
            String.format("User with id: %s does not exists", userId));
        userHandler.checkUserInput(
            !optionalUser.get().getEmail().equalsIgnoreCase(authenticatedUserEmail.getAuthenticatedUserEmail()),
            "Permission denied!");

        return new ArrayList<>(friendshipRepository.findAllByOwnerAndFriendshipStatus(optionalUser.get(), friendshipStatus));
    }
}
