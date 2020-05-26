package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    @Size(min = 1, max = 50)
    private String title;

    @CreationTimestamp
    private LocalDateTime appointmentTime;

    @Size(max = 5000)
    private String description;

    @Min(value = 0, message = "Only positive number of minutes allowed")
    private int estimatedTime;

    @JsonIgnoreProperties("appointments notes todos friendship")
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

