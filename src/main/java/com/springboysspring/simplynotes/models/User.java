package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity(name = "Users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonManagedReference
    @OneToMany(fetch = LAZY,
            mappedBy = "owner",
            cascade = ALL)
    private Set<Note> notes = new HashSet<>();

    @JsonManagedReference
    @OneToMany(fetch = LAZY,
            mappedBy = "owner",
            cascade = ALL)
    private Set<ToDo> todos = new HashSet<>();

    // Något med den som gör att man ej kan kalla på rest apin.
//    @JsonManagedReference
    @ManyToMany(fetch = LAZY, mappedBy = "attendees")
    private Set<Appointment> appointments = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "owner", cascade = ALL, orphanRemoval = true)
    private Set<Friendship> friendships = new HashSet<>();

    public void addFriend(User friend) {
        friendships.add(new Friendship(this, friend));
        friend.friendships.add(new Friendship(friend, this));
    }

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
