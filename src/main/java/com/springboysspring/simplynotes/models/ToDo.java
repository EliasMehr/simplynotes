package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String content;
    private LocalDateTime deadline;

    @JsonBackReference
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Enumerated(STRING)
    private Priority priority;
}
