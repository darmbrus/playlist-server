package com.davidarmbrust.spi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides web exception handling.
 */
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleConflict(Exception ex, HttpServletRequest request) {
        HttpStatus httpStatus;
        httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<String>("Exception: " + ex.getMessage(), httpStatus);
    }
}
