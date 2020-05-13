package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Friendship {

    public Friendship(User owner, User friend) {
        this.owner = owner;
        this.friend = friend;
    }


    @JsonManagedReference
    @EmbeddedId
    @JsonIgnoreProperties(value = {"ownerId","friendId"})
    private FriendshipId friendshipId = new FriendshipId();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("ownerId")
    private User owner;


    @JsonIgnoreProperties({"friends", "password","friendships"})
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapsId("friendId")
    private User friend;

    @Enumerated(EnumType.STRING)
    private Status status;


    public enum Status {
        ACCEPTED, PENDING, DECLINED
    }

    @Data
    @NoArgsConstructor
    @Embeddable
    public static class FriendshipId implements Serializable {
        private UUID ownerId;
        private UUID friendId;
    }
}
