package com.springboysspring.simplynotes.services.handlers;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserHandler {

    @Transactional
    public void invoke(UUID userId, UUID friendId, String authenticatedUserEmail,
        BiConsumer<User, User> handleTheUserRequest,
        UserRepository userRepository) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User currentUser = user.get();
            checkUserInput(
                doesUserEmailEqualsAuthenticatedUserEmail(authenticatedUserEmail, currentUser),
                "You dont have the permission to add/delete friends for other users!");

            checkUserInput(
                userId.equals(friendId),
                "You cannot add/delete yourself as a friend!");

            Optional<User> friend = userRepository.findById(friendId);
            if (friend.isPresent()) {
                User friendObject = friend.get();
                handleTheUserRequest.accept(currentUser, friendObject);
            } else {
                throw new APIRequestException(String.format("Friend with the id: %s does not exists!", friendId));
            }
            userRepository.save(currentUser);
        } else {
            throw new APIRequestException(String.format("User with the id: %s does not exists!", userId));
        }
    }

    public boolean doesUserEmailEqualsAuthenticatedUserEmail(String authenticatedUserEmail, User currentUser) {
        return !currentUser.getEmail().contentEquals(authenticatedUserEmail);
    }

    public void checkUserInput(boolean isUserInputWrong, String message) {
        if (isUserInputWrong)  throw new APIRequestException(message);
    }



}
