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
        User currentUser = verifyInputtedId(userId);
        User friendObject = verifyInputtedId(friendId);
        checkForAuthentication(currentUser, authenticatedUserEmail.getAuthenticatedUserEmail());
        checkBooleanOrElseThrow(userId.equals(friendId), "You cannot add/delete yourself as a friend!");
        handleTheUserRequest.accept(currentUser, friendObject);
        userRepository.save(currentUser);
    }

    public void checkForAuthentication(User currentUser, String authenticatedUserEmail) {
        boolean isUserAuthenticated = isUserEmailSameAsAuthenticatedUserEmail(authenticatedUserEmail, currentUser);
        checkBooleanOrElseThrow(isUserAuthenticated, "You dont have the permission to add/delete friends for other users!");
    }

    public User verifyInputtedId(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        checkBooleanOrElseThrow(optionalUser.isEmpty(), String.format("User with id: %s does not exists", userId));
        return optionalUser.get();
    }

    public void checkBooleanOrElseThrow(boolean userInputtedValue, String message) {
        if (userInputtedValue) {
            throw new APIRequestException(message);
        }
    }

    public boolean isUserEmailSameAsAuthenticatedUserEmail(String authenticatedUserEmail, User currentUser) {
        return !currentUser.getEmail().contentEquals(authenticatedUserEmail);
    }


}
