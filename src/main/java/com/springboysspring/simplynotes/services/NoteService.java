package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.models.Note;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.NoteRepository;
import com.springboysspring.simplynotes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {

    private NoteRepository noteRepository;
    private UserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public User getUser(UUID id) throws Exception { // Kanske kan vara en metod i userService ist
        Optional<User> owner = userRepository.findById(id);
        if (owner.isPresent()) {
            return owner.get();
        }
        throw new Exception("User not Found");
    }

    public List<Note> getNotesByUserID(UUID id) throws Exception {
        return noteRepository.findAllByOwner(getUser(id));
    }

    public void addNoteToUserId(UUID id, Note note) throws Exception {
        getUser(id).addNote(note);
        try {
            noteRepository.save(note);
        } catch (Exception e) {
            throw new Exception("Could not persist Note to Database");
        }
    }

    public void deleteNoteById(UUID id) throws Exception {
        try {
            noteRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("No note with that id exists");
        }
    }

    public void updateNoteById(UUID id, Note newNote) throws Exception {
        Optional<Note> oldNote = noteRepository.findById(id);
        if (oldNote.isPresent()) {
            oldNote.get().setTitle(newNote.getTitle());
            oldNote.get().setContent(newNote.getContent());
            try {
                noteRepository.save(oldNote.get());
            } catch (Exception e) {
                throw new Exception("Could not persist Note to Database");
            }
        } else {
            throw new Exception("No note with that id exists");
        }
    }
}
