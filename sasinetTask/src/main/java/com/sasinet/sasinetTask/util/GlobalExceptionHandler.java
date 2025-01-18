package com.sasinet.sasinetTask.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public  ResponseEntity<Map<String, String>> handlePasswordMismatchException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);  // 401 Unauthorized
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        // Create ErrorResponse object and set the error message
        ErrorResponse errorResponse = new ErrorResponse(ex.getReason());

        // Return response entity with status and error message
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }
}

