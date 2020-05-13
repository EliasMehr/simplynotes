package com.springboysspring.simplynotes.controllers;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.response.Response;
import com.springboysspring.simplynotes.services.UserService;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok(new Response("Account is created successfully!"));
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> searchUsers(@RequestParam(required = false) String firstName, String lastName, String email) {
        try {
            var users = userService.searchUsers(firstName, lastName, email);
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/user/{userId}/{friendId}")  // OR WE CAN TAKE A WHOLE FRIEND AS A PARAMETER?
    public ResponseEntity<?> addFriend(@PathVariable UUID userId,@PathVariable UUID friendId) {
        try {
            userService.addFriend(userId, friendId);
            return ResponseEntity.ok(new Response("Friend added successfully!"));
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }
}
