package com.springboysspring.simplynotes.controllers;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.springboysspring.simplynotes.models.Friendship;
import com.springboysspring.simplynotes.models.FriendshipStatus;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.response.Response;
import com.springboysspring.simplynotes.services.UserService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
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

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping()
    public ResponseEntity<?> searchUsers(@RequestParam(required = false) String firstName, String lastName, String email) {
        try {
            var users = userService.searchUsers(firstName, lastName, email);
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId}/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable UUID userId, @PathVariable UUID friendId) {
        try {
            userService.addFriend(userId, friendId);
            return ResponseEntity.ok(new Response("Friend request sent successfully!"));
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{userId}/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable UUID userId, @PathVariable UUID friendId) {
        try {
            userService.deleteFriend(userId, friendId);
            return ResponseEntity.ok(new Response("Friend deleted successfully!"));
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/friends")
    public ResponseEntity<?> getUsersFriends(@PathVariable UUID userId) {
        try {
            List<Friendship> friendsByStatus = userService.getAllFriends(userId);
            return ResponseEntity.ok().body(friendsByStatus);
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getFriendsByStatus(@PathVariable UUID userId,
        @RequestParam(name = "status") FriendshipStatus friendshipStatus) {
        try {
            List<Friendship> friendsByStatus = userService.getFriendsByStatus(userId, friendshipStatus);
            return ResponseEntity.ok().body(friendsByStatus);
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/{friendId}")
    public ResponseEntity<?> changeFriendshipStatus(@PathVariable UUID userId,
        @PathVariable UUID friendId,
        @RequestParam FriendshipStatus status) {
        try {
            String message = userService.changeFriendshipStatus(userId, friendId, status);
            return ResponseEntity.ok().body(new Response(message));
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }
}
