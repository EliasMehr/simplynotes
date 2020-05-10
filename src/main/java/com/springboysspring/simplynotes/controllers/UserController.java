package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import com.springboysspring.simplynotes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/")
public class UserController {



    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @Autowired
    UserRepository userRepository;

//    @PostMapping("/register")
//    public User createUser(@RequestBody User user) {
//        user.setRole(user.defaultRole());
//        return userRepository.saveAndFlush(user);
//    }

    @GetMapping("/home")
    public String helloHome() {
        return "Hello HomePaGE";
    }





}
