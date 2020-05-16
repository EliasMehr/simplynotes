package com.springboysspring.simplynotes.services.handlers;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.UserRepository;
import com.springboysspring.simplynotes.services.AuthenticatedUserEmail;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHandler {

    private final UserRepository userRepository;
    private final AuthenticatedUserEmail authenticatedUserEmail;

    @Autowired
    public UserHandler(UserRepository userRepository, AuthenticatedUserEmail authenticatedUserEmail) {
        this.userRepository = userRepository;
        this.authenticatedUserEmail = authenticatedUserEmail;
    }

    @Transactional
    public void invoke(UUID userId, UUID friendId, BiConsumer<User, User> handleTheUserRequest) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User currentUser = user.get();
            verifyUserInputOrElseThrowException(
                isUserEmailSameAsAuthenticatedUserEmail(authenticatedUserEmail.getAuthenticatedUserEmail(), currentUser),
                "You dont have the permission to add/delete friends for other users!");

            verifyUserInputOrElseThrowException(
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

    public void checkForAuthentication(User currentUser, String authenticatedUserEmail) {
        boolean isUserAuthenticated = isUserEmailSameAsAuthenticatedUserEmail(authenticatedUserEmail, currentUser);
        verifyUserInputOrElseThrowException(isUserAuthenticated, "Permission denied!");
    }

    public void isUserAMember(UUID userId, Optional<User> optionalFriend) {
        verifyUserInputOrElseThrowException(
            optionalFriend.isEmpty(),
            String.format("User with id: %s does not exists", userId));
    }

    public boolean isUserEmailSameAsAuthenticatedUserEmail(String authenticatedUserEmail, User currentUser) {
        return !currentUser.getEmail().contentEquals(authenticatedUserEmail);
    }

    public void verifyUserInputOrElseThrowException(boolean isUserInputWrong, String message) {
        if (isUserInputWrong) {
            throw new APIRequestException(message);
        }
    }

    public void verifyUsers(UUID userId, UUID friendId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        isUserAMember(userId, optionalUser);
        Optional<User> friendOptional = userRepository.findById(friendId);
        isUserAMember(friendId, friendOptional);
        checkForAuthentication(optionalUser.get(), authenticatedUserEmail.getAuthenticatedUserEmail());
    }
}
