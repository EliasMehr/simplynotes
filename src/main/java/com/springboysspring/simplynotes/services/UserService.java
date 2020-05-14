package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import com.springboysspring.simplynotes.services.handlers.AddFriendHandler;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(User user) {
        Optional<User> isEmailTaken = userRepository.findByEmail(user.getEmail());
        if (isEmailTaken.isPresent()) {
            throw new APIRequestException(String.format("Email %s is already taken!", user.getEmail()));
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            throw new APIRequestException("Error establishing a database connection!");
        }
    }

    //TODO ignore case s...
    public List<User> searchUsers(String firstName, String lastName, String email) {
        try {
            return userRepository.findByFirstNameOrLastNameOrEmail(firstName, lastName, email);
        } catch (Exception e) {
            throw new APIRequestException("Error establishing a database connection!");
        }
    }

    public void addFriend(UUID userId, UUID friendId) {
        new AddFriendHandler()
            .invoke(
                userId,
                friendId,
                getAuthenticatedUserEmail(),
                userRepository);
    }

    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal()
            .toString();
    }


}
