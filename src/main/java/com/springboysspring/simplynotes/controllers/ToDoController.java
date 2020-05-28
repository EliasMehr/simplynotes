package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.ToDo;
import com.springboysspring.simplynotes.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ToDoController {
    private ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/todos/user/{id}")
    public ResponseEntity<List<ToDo>> getTodosByUserID(@PathVariable UUID id) {
        List<ToDo> response;
        try {
            response = toDoService.getTodosByUserID(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/todos/user/{id}")
    public ResponseEntity<String> addTodoToUserId(@PathVariable UUID id, @RequestBody ToDo toDo) {
        try {
            toDoService.addTodoToUserId(id, toDo);
            return ResponseEntity.ok("Todo added to user");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/todos/{id}")
    public ResponseEntity<String> updateTodoById(@PathVariable UUID id, @RequestBody ToDo toDo) {
        try {
            toDoService.updateTodoById(id, toDo);
            return ResponseEntity.ok("Todo updated");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable UUID id) {
        try {
            toDoService.deleteTodoById(id);
            return ResponseEntity.ok("Todo was deleted");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
