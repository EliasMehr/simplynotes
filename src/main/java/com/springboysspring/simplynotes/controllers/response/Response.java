package com.springboysspring.simplynotes.controllers.response;

import java.sql.Timestamp;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


@Data
public class Response {

    private final String message;
    @CreationTimestamp
    private Timestamp timestamp;

    public Response(String message) {
        this.message = message;
    }
}
