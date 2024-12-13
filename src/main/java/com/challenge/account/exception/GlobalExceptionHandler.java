package com.challenge.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException ex) {
        Error errorResponse = new Error()
                .title("Bad Request")
                .detail(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleGeneralException(Exception ex) {
        Error errorResponse = new Error()
                .title("Internal Server Error")
                .detail(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

class Error {
    private String title;
    private String detail;
    private int status;

    public Error title(String title) {
        this.title = title;
        return this;
    }

    public Error detail(String detail) {
        this.detail = detail;
        return this;
    }

    public Error status(int status) {
        this.status = status;
        return this;
    }
}