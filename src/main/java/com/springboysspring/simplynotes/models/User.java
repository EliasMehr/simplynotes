package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;

import static com.springboysspring.simplynotes.models.FriendshipStatus.ACCEPTED;
import static com.springboysspring.simplynotes.models.FriendshipStatus.PENDING;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity(name = "Users")
@Getter
@Setter
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "([a-zA-ZAÖÅöäå]{2,})|([a-zA-ZöäåÅÖÄ-]){2,}([ ]?)([a-zA-ZöäåÅÖÄ]){2,}", message = "Invalid name format")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "([a-zA-ZAÖÅöäå]{2,})|([a-zA-ZöäåÅÖÄ-]){2,}([ ]?)([a-zA-ZöäåÅÖÄ]){2,}", message = "Invalid name format")
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(unique = true)
    @Pattern(regexp = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)@[A-Za-z0-9]+(.[A-Za-z0-9]+)(.[A-Za-z]{2,})$", message = "Invalid email format")
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "role_id")
    private Role role = defaultRole();

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

    @ManyToMany(fetch = EAGER, mappedBy = "attendees")
    private Set<Appointment> appointments = new HashSet<>();

//    @JsonManagedReference

    @OneToMany(fetch = EAGER, mappedBy = "owner", cascade = ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"friendshipId"})
    private Set<Friendship> friendships = new HashSet<>();

    public Role defaultRole() {
        Role role = new Role();
        role.setType(Type.USER);
        return role;
    }

    public void addFriend(User friend) {
        Friendship friendship = new Friendship(this, friend);
        friendship.setFriendshipStatus(PENDING);
        friendships.add(friendship);
        Friendship friendFriendShip = new Friendship(friend, this);
        friendFriendShip.setFriendshipStatus(PENDING);
        friend.friendships.add(friendFriendShip);
    }

    public void deleteFriend(User friend){
        Friendship ownerFriendship = getFriendshipByUser(this.friendships,friend);
        Friendship friendsFriendships = getFriendshipByUser(friend.friendships, this);
        this.friendships.remove(ownerFriendship);
        friend.friendships.remove(friendsFriendships);
    }

    @NotNull
    private Friendship getFriendshipByUser(Set<Friendship> friendships,User friend) {
        return friendships.stream()
            .filter(e -> e.getFriend().equals(friend)).findAny().get();
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
