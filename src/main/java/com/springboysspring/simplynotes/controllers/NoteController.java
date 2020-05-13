package com.springboysspring.simplynotes.controllers;

import com.springboysspring.simplynotes.models.Note;
import com.springboysspring.simplynotes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class NoteController {

    private NoteService noteService;
    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/notes/user/{id}")
    public ResponseEntity<List<Note>> getNotesByUserID(@PathVariable UUID id) {
        List<Note> response;
        try {
            response = noteService.getNotesByUserID(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/notes/user/{id}")
    public ResponseEntity<String> addNoteToUserId(@PathVariable UUID id, @RequestBody Note note) {
        try {
            noteService.addNoteToUserId(id, note);
            return ResponseEntity.ok("Note added to user");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/notes/{id}")
    public ResponseEntity<String> updateNoteById(@PathVariable UUID id, @RequestBody Note note) {
        try {
            noteService.updateNoteById(id, note);
            return ResponseEntity.ok("Note updated");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<String> deleteNoteById(@PathVariable UUID id) {
        try {
            noteService.deleteNoteById(id);
            return ResponseEntity.ok("Note was deleted");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/notes/{noteId}/friend/{friendId}")
    public void sendNoteToFriend(@PathVariable UUID noteId, UUID friendId) {
        // find note by id
        // find user and then his friend == friendName
        // make new Note with copy users note attr
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/notes/{id}/convert")
    public void convertNoteAsTodo(@PathVariable UUID id) {
        // find note by id
        // copy note, create to.do
        // delete note
        // add to.do to db
    }
}
