package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void register(User user) {
        Optional<User> isEmailTaken = userRepository.findByEmail(user.getEmail());

        if (isEmailTaken.isPresent()) {
            throw new APIRequestException("Email is already taken: " + user.getEmail());
        }
        userRepository.saveAndFlush(user);
    }
}
