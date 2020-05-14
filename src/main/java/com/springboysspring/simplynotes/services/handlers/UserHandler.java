package com.springboysspring.simplynotes.services.handlers;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

public class UserHandler {


    public void invoke(UUID userId, UUID friendId,
        String authenticatedUserEmail,
        BiConsumer<User, User> handleTheUserRequest,
        UserRepository userRepository) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User currentUser = user.get();
            if (!currentUser.getEmail().equalsIgnoreCase(authenticatedUserEmail)) {
                throw new APIRequestException("You dont have the permission to add or delete friends for other users!");
            }
            if (userId.equals(friendId)) {
                throw new APIRequestException("You cannot add yourself as a friend!");
            }

            Optional<User> friend = userRepository.findById(friendId);
            if (friend.isPresent()) {
                User friendObject = friend.get();
                handleTheUserRequest.accept(currentUser, friendObject);
            } else {
                throw new APIRequestException(String.format("Friend with the id: %s does not exists!", friendId));
            }
            try {
                userRepository.save(currentUser);
            } catch (Exception e) {
                throw new APIRequestException("Error establishing the database connection!");
            }
        } else {
            throw new APIRequestException(String.format("User with the id: %s does not exists!", userId));
        }
    }


}
