package com.springboysspring.simplynotes.services;

import com.springboysspring.simplynotes.models.Note;
import com.springboysspring.simplynotes.models.ToDo;
import com.springboysspring.simplynotes.models.User;
import com.springboysspring.simplynotes.repositories.NoteRepository;
import com.springboysspring.simplynotes.repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {

    private NoteRepository noteRepository;
    private ToDoRepository toDoRepository;
    private UserService userService;

    @Autowired
    public NoteService(NoteRepository noteRepository, ToDoRepository toDoRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.toDoRepository = toDoRepository;
        this.userService = userService;
    }

    private void isNoteValid(Note note) throws Exception {
        if (note.getTitle() == null) {
            throw new Exception("Invalid Request Body, Note is null");
        }
    }

    public List<Note> getNotesByUserID(UUID id) throws Exception {
        return noteRepository.findAllByOwner(userService.getUser(id));
    }


    public void addNoteToUserId(UUID id, Note note) throws Exception {
        isNoteValid(note);

        userService.getUser(id).addNote(note);
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
        isNoteValid(newNote);

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

    public void sendNoteToFriend(UUID noteId, UUID friendId) throws Exception {
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isPresent()) {
            Note noteToFriend = new Note();
            User friend = userService.getUser(friendId);
            noteToFriend.setOwner(friend);
            noteToFriend.setTitle(note.get().getTitle());
            noteToFriend.setContent(String.format("%s\n\nFrom: %s", note.get().getContent(), note.get().getOwner().getFirstName()));
            friend.addNote(noteToFriend);

            try {
                noteRepository.save(noteToFriend);
            } catch (Exception e) {
                throw new Exception("Could note persist Friends Note to Database");
            }

        } else {
            throw new Exception("No note with that id exists");
        }
    }

    public void convertNoteAsTodo(UUID id) throws Exception {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            ToDo todo = new ToDo();
            todo.setOwner(note.get().getOwner());
            todo.setTitle(note.get().getTitle());
            todo.setContent(note.get().getContent());

            try {
                noteRepository.delete(note.get());
            } catch (Exception e) {
                throw new Exception("Could not delete Note in Database");
            }
            try {
                toDoRepository.save(todo);
            } catch (Exception e) {
                throw new Exception("Could not save Todo in Database");
            }
        } else {
            throw new Exception("No note with that id exists");
        }
    }
}
