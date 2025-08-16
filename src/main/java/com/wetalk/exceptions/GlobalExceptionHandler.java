package com.wetalk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Centralized exception handling for controllers.
 * - Converts thrown exceptions to consistent HTTP responses.
 * - Extend with specific handlers (e.g., MethodArgumentNotValidException) as needed.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /** Fallback handler for uncaught exceptions -> 500 Internal Server Error. */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Add more @ExceptionHandler methods here for domain-specific errors.
}