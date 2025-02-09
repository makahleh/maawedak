package com.mak.mawedak.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.AuthenticationException;

import java.security.SignatureException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Database Errors (e.g., duplicate keys, missing required data)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDatabaseError(DataIntegrityViolationException ex) {
        String errorMessage = "Database error: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST); // 400
    }

    // Handle Login/Authentication Errors
    @ExceptionHandler({AuthenticationException.class, SignatureException.class})
    public ResponseEntity<Object> handleLoginError(AuthenticationException ex) {
        String errorMessage = "Authentication failed: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED); // 401
    }

    // Handle Entity Not Found (e.g., when a resource is not found in DB)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        String errorMessage = "Resource not found: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND); // 404
    }

    // Handle Validation Errors (e.g., invalid input data)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationError(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        BindingResult result = ex.getBindingResult();
        for (FieldError fieldError : result.getFieldErrors()) {
            errorMessage.append(fieldError.getField())
                    .append(" - ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }
        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST); // 400
    }

    // Handle general RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        String errorMessage = "Stop! error: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    // Handle all other exceptions globally
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        String errorMessage = "An unexpected error occurred: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
}
