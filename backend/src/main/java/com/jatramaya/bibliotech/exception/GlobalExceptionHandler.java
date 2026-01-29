package com.jatramaya.bibliotech.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.io.IOException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(DuplicateCredentialException.class)
        public ResponseEntity<Map<String, String>> handleDuplicateCredential(DuplicateCredentialException ex) {
                Map<String, String> response = Map.of(
                                "status", "error",
                                "message", ex.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(response);
        }

        @ExceptionHandler(InvalidPasswordException.class)
        public ResponseEntity<Map<String, String>> handleBadPassword(InvalidPasswordException ex) {
                Map<String, String> response = Map.of(
                                "status", "error",
                                "message", ex.getMessage());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Map<String, String>> handleIntegrityError(DataIntegrityViolationException ex) {
                Map<String, String> response = Map.of(
                                "status", "error",
                                "message", ex.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException ex) {
                Map<String, String> response = Map.of(
                                "status", "error",
                                "message", ex.getMessage());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, Object>> handleBadRequestBody(HttpMessageNotReadableException ex) {

                Map<String, Object> response = Map.of(
                                "status", "error",
                                "message", ex.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(IOException.class)
        public ResponseEntity<?> handleIOException(IOException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                                "status", "error",
                                "message", ex.getMessage()));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<?> handleIllegalArgsException(IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                                "status", "error",
                                "message", ex.getMessage()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleBadRequest(MethodArgumentNotValidException ex) {
                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult().getFieldErrors()
                                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

                ex.getBindingResult().getGlobalErrors()
                                .forEach(err -> errors.put(err.getObjectName(), err.getDefaultMessage()));

                Map<String, Object> response = Map.of(
                                "status", "error",
                                "message", errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(response);
        }

}
