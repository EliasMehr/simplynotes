package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.Note;
import com.springboysspring.simplynotes.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @PostMapping
    public Note create(@RequestBody Note note) {
        return noteRepository.saveAndFlush(note);
    }
}
