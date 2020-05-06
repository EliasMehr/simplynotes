package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private LocalDateTime appointmentTime;
    private String description;
    private int estimatedTime;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "appointment_users",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> attendees = new HashSet<>();

    public void addAttendee(User attendee) {
        attendees.add(attendee);
        attendee.getAppointments().add(this);
    }

    public void removeAttendee(User attendee) {
        attendees.remove(attendee);
        attendee.getAppointments().remove(this);
    }
}

