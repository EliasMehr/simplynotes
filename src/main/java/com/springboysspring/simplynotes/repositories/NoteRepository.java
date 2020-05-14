package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.Note;
import com.springboysspring.simplynotes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

    public List<Note> findAllByOwner(User user);

}
