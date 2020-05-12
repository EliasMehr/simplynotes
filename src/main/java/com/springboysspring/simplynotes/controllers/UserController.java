package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {


    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/v1")
    public List<User> get() {
        return userService.get();
    }

    @PostMapping("/login")
    public void login(@RequestBody Optional<User> user) {
        userService.login(user);
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.register(user);
    }


}
