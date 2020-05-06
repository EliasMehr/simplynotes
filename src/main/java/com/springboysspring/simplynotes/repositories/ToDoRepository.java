package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ToDoRepository extends JpaRepository<ToDo, UUID> {
}
