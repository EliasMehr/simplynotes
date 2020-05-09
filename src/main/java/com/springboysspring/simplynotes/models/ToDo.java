package com.springboysspring.simplynotes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 1, max = 20)
    private String title;

    @NotNull
    private LocalDateTime timestamp = LocalDateTime.now();

    @Size(max = 5000)
    private String content;


    private LocalDateTime deadline;

    @JsonBackReference
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Enumerated(STRING)
    private Priority priority;
}
