package com.springboysspring.simplynotes.repositories;

import com.springboysspring.simplynotes.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
}
