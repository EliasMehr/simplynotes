package com.springboysspring.simplynotes.controllers.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class Response {

    private Timestamp timestamp;
    private HttpStatus status;
    private final String message;

    public Response(String message) {
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.status = HttpStatus.OK;
        this.message = message;
    }
}
