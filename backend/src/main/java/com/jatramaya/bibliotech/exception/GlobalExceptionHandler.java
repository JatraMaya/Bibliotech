package com.jatramaya.bibliotech.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
                Map<String, String> response = Map.of("status", "error", "message", ex.getMessage());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleBadRequest(MethodArgumentNotValidException ex) {
                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult().getFieldErrors()
                                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

                Map<String, Object> response = Map.of(
                                "status", "error",
                                "message", errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(response);
        }

}
