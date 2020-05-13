package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.exceptions.APIRequestException;
import com.springboysspring.simplynotes.models.ToDo;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.ToDoRepository;
import com.springboysspring.simplynotes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoService(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    public List<ToDo> getTodo(UUID id) {
        Optional<User> userById = userRepository.findById(id);

        if (userById.isPresent()) {
            return toDoRepository.findAllByOwnerId(id);
        }
        throw new APIRequestException("User by this id: " + id + " does not exist");
    }

    public ToDo create(ToDo todo) {


        return null;
    }
}
