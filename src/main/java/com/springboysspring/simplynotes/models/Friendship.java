package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Friendship {

    @EmbeddedId
    @JsonIgnore
    private FriendshipId friendshipId = new FriendshipId();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ownerId")
    private User owner;

    @JsonIgnoreProperties({"friends", "password"})
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("friendId")
    private User friend;

    @Enumerated(EnumType.STRING)
    private Status status;


    public enum Status {
        ACCEPTED, PENDING, DECLINED
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Embeddable
    public static class FriendshipId implements Serializable {
        private UUID ownerId;
        private UUID friendId;
    }
}
