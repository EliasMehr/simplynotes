package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.security.auth.MyUserDetails;
import com.springboysspring.simplynotes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {


    private UserService userService;
    private MyUserDetails currentUser;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

  //  @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/v1")
    public User get() {
        currentUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currentUser.getCurrentUser();
    }


}
