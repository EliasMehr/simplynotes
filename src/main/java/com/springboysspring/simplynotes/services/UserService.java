package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.FriendshipReposity;
import com.springboysspring.simplynotes.repositories.UserRepository;
import com.springboysspring.simplynotes.services.handlers.UserHandler;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticatedUserEmail authenticatedUserEmail;
    private final FriendshipReposity friendshipReposity;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
        AuthenticatedUserEmail authenticatedUserEmail, FriendshipReposity friendshipReposity) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticatedUserEmail = authenticatedUserEmail;
        this.friendshipReposity = friendshipReposity;
    }

    public void register(User user) {
        Optional<User> isEmailTaken = userRepository.findByEmail(user.getEmail());
        if (isEmailTaken.isPresent()) {
            throw new APIRequestException(String.format("Email %s is already taken!", user.getEmail()));
        }
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
        new UserHandler()
            .invoke(
                userId,
                friendId,
                authenticatedUserEmail.getAuthenticatedUserEmail(),
                User::addFriend,
                userRepository);
    }

    public void deleteFriend(UUID userId, UUID friendId) {
        new UserHandler()
            .invoke(
                userId,
                friendId,
                authenticatedUserEmail.getAuthenticatedUserEmail(),
                User::deleteFriend,
                userRepository);
    }



}
