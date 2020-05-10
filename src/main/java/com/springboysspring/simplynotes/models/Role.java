package com.springboysspring.simplynotes.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.EnumType.*;

@Entity
@Data
public class Role {

    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Enumerated(STRING)
    private Type type;

    public Role() {

    }

    public Role(Type type) {
        this.type = type;
    }
}
