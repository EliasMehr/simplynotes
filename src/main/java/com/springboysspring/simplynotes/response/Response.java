package com.springboysspring.simplynotes.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class Response {

    private final String message;

}
