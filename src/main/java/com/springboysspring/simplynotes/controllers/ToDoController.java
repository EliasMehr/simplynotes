package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.ToDo;
import com.springboysspring.simplynotes.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todo")
public class ToDoController {

    private final TodoService todoService;

    @Autowired
    public ToDoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<ToDo> getAll(UUID id) {
        return todoService.getTodo(id);
    }


}
