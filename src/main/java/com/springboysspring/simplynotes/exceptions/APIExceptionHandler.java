package com.springboysspring.simplynotes.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class APIExceptionHandler {

    @ExceptionHandler(value = APIRequestException.class)
    public ResponseEntity<Object> handleApiException(APIRequestException exception) {
        APIException apiException = new APIException(exception.getMessage(), BAD_REQUEST);
        return new ResponseEntity<>(apiException, BAD_REQUEST);
    }
}
