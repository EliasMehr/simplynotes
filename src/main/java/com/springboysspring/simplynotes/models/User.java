package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Users")
@Data
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

    @JsonManagedReference
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
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
