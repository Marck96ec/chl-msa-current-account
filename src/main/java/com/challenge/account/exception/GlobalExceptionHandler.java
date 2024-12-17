package com.challenge.account.exception;

import com.challenge.customer.server.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<com.challenge.customer.server.models.Error> handleIllegalArgumentException(IllegalArgumentException ex) {
        com.challenge.customer.server.models.Error errorResponse = new com.challenge.customer.server.models.Error()
                .title("Bad Request")
                .detail(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<com.challenge.customer.server.models.Error> handleGeneralException(Exception ex) {
        com.challenge.customer.server.models.Error errorResponse = new Error()
                .title("Internal Server Error")
                .detail(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}