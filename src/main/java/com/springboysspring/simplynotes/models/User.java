package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "owner",
            cascade = CascadeType.ALL)
    private Set<Note> notes = new HashSet<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "owner",
            cascade = CascadeType.ALL)
    private Set<ToDo> todos = new HashSet<>();

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attendees")
    private Set<Appointment> appointments = new HashSet<>();


    public void addNote(Note note) {
        notes.add(note);
        note.setOwner(this);
    }

    public void removeNote(Note note) {
        notes.remove(note);
        note.setOwner(null);
    }

    public void addTodo(ToDo todo) {
        todos.add(todo);
        todo.setOwner(this);
    }

    public void removeTodo(ToDo todo) {
        todos.remove(todo);
        todo.setOwner(null);
    }

}
