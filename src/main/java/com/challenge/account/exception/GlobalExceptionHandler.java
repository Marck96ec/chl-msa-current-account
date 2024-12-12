package com.challenge.account.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException ex) {
//        Error errorResponse = new Error()
//                .title("Bad Request")
//                .detail(ex.getMessage())
//                .status(HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Error> handleGeneralException(Exception ex) {
//        Error errorResponse = new Error()
//                .title("Internal Server Error")
//                .detail(ex.getMessage())
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}