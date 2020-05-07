package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @PostMapping("/register")
    public void create(@RequestBody User user) {
        // TODO
    }

}
