package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class UserController {


    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        if (user != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.register(user);
            return ResponseEntity.ok("User has been created successfully");
        }
        throw new APIRequestException("Failed to register user");
    }

}
