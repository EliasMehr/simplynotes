package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue
    private UUID id;


    @NotBlank
    @Size(min = 1, max = 20)
    private String title;

    private LocalDateTime appointmentTime;

    @Size(max = 5000)
    private String description;

    @Min(value = 0, message = "Only positive number of minutes allowed")
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

