package com.springboysspring.simplynotes.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class APIException {

    private final String message;
    private final HttpStatus httpStatus;



}
