package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.Role;
import com.springboysspring.simplynotes.models.Type;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public void create(@RequestBody User user) {
        Role role = new Role();
        role.setType(Type.USER);
        user.setRole(role);
        userRepository.saveAndFlush(user);

    }

}
