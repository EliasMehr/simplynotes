package com.springboysspring.simplynotes.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.EnumType.*;

@Entity
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(STRING)
    private Type type;
}
