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

@Entity
@Data
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(min = 1, max = 20)
    private String title;

    @Size(max = 5000)
    private String content;

    @NotNull
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;


}
