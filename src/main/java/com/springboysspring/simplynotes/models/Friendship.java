package com.springboysspring.simplynotes.models;

import static com.springboysspring.simplynotes.models.FriendshipStatus.ACCEPTED;
import static com.springboysspring.simplynotes.models.FriendshipStatus.DECLINED;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

//    @JsonManagedReference
    @EmbeddedId
    @JsonIgnoreProperties(value = {"ownerId","friendId"})
    private FriendshipId friendshipId = new FriendshipId();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @MapsId("ownerId")
    private User owner;


    @JsonIgnoreProperties({"friends", "password","friendships"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @MapsId("friendId")
    private User friend;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus friendshipStatus;


    @Data
    @NoArgsConstructor
    @Embeddable
    public static class FriendshipId implements Serializable {
        private UUID ownerId;
        private UUID friendId;
    }

    public void acceptFriendRequest(Friendship friend){
        this.setFriendshipStatus(ACCEPTED);
        friend.setFriendshipStatus(ACCEPTED);
    }

    public void declineFriendRequest(Friendship friend){
        this.setFriendshipStatus(DECLINED);
        friend.setFriendshipStatus(DECLINED);
    }
}
