package com.springboysspring.simplynotes.services;

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
public
class ToDoService {
    private ToDoRepository toDoRepository;
    private UserRepository userRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    public User getUser(UUID id) throws Exception { // Kanske kan vara en metod i userService ist
        Optional<User> owner = userRepository.findById(id);
        if (owner.isPresent()) {
            return owner.get();
        }
        throw new Exception("User not Found");
    }

    public List<ToDo> getTodosByUserID(UUID id) throws Exception {
        return toDoRepository.findAllByOwner(getUser(id));
    }

    public void addTodoToUserId(UUID id, ToDo toDo) throws Exception {
        getUser(id).addTodo(toDo);
        try {
            toDoRepository.save(toDo);
        } catch (Exception e) {
            throw new Exception("Could not persist Todo to Database");
        }
    }

    public void updateTodoById(UUID id, ToDo newToDo) throws Exception {
        Optional<ToDo> oldToDo = toDoRepository.findById(id);
        if (oldToDo.isPresent()) {
            oldToDo.get().setTitle(newToDo.getTitle());
            oldToDo.get().setContent(newToDo.getContent());
            oldToDo.get().setPriority(newToDo.getPriority());
            try {
                toDoRepository.save(oldToDo.get());
            } catch (Exception e) {
                throw new Exception("Could not persist Todo to Database");
            }
        } else {
            throw new Exception("No Todo with that id exists");
        }
    }

    public void deleteTodoById(UUID id) throws Exception {
        try {
            toDoRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("No Todo with that id exists");
        }
    }
}