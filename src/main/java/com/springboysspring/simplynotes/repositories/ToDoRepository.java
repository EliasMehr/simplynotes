package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, UUID> {

    List<ToDo> findAllByOwnerId(UUID owner_id);
}
