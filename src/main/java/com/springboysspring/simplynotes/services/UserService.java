package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> login(Optional<User> user) {

        user = userRepository.findByEmail(user.get().getEmail());

        if (user.isPresent()) {
            System.out.println("Success");
            return user;
        }
        throw new IllegalStateException("Failure");
    }

    public void register(User user) {
        userRepository.saveAndFlush(user);
    }

    public List<User> get() {
        return userRepository.findAll();
    }

    public void update(User user) {
        userRepository.saveAndFlush(user);
    }
}
