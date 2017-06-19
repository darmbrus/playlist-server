package com.davidarmbrust.spi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleConflict(Exception ex, HttpServletRequest request) {
        log.error("Unexpected exception occurred" + ex.getMessage(), ex);
        HttpStatus httpStatus;
        httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<String>("Exception: " + ex.getMessage(), httpStatus);
    }
}
