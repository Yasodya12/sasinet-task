package com.sasinet.sasinetTask.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles IllegalArgumentException and returns a bad request response.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    // Handles all global exceptions and returns a NOT_FOUND response with an error message.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    // Handles AuthenticationException and returns a bad request response.
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    // Handles ResponseStatusException and returns a response with the status and error message.
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        // Create ErrorResponse object with the error message
        ErrorResponse errorResponse = new ErrorResponse(ex.getReason());
        // Return response entity with the status code and error message
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

}

