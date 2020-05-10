package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity(name = "Users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 25)
    @Pattern(regexp = "([a-zA-ZAÖÅöäå]{2,})|([a-zA-ZöäåÅÖÄ-]){2,}([ ]?)([a-zA-ZöäåÅÖÄ]){2,}", message = "Invalid name format")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String username;

    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "([a-zA-ZAÖÅöäå]{2,})|([a-zA-ZöäåÅÖÄ-]){2,}([ ]?)([a-zA-ZöäåÅÖÄ]){2,}", message = "Invalid name format")
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 25)
    @Column(unique = true)
    @Pattern(regexp = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)@[A-Za-z0-9]+(.[A-Za-z0-9]+)(.[A-Za-z]{2,})$", message = "Invalid email format")
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonManagedReference
    @OneToMany(fetch = EAGER,
            mappedBy = "owner",
            cascade = ALL)
    private Set<Note> notes = new HashSet<>();


    @JsonManagedReference
    @OneToMany(fetch = EAGER,
            mappedBy = "owner",
            cascade = ALL)
    private Set<ToDo> todos = new HashSet<>();

    @JsonIgnoreProperties("attendees")
    @ManyToMany(fetch = EAGER, mappedBy = "attendees")
    private Set<Appointment> appointments = new HashSet<>();

    @JsonManagedReference
    @OneToMany(fetch = EAGER,mappedBy = "owner", cascade = ALL, orphanRemoval = true)
    private Set<Friendship> friendships = new HashSet<>();

    public User(String username, String firstName, String lastName, String email, String encode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = encode;
        this.username = username;
    }


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
