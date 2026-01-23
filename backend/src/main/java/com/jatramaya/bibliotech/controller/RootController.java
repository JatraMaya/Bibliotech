package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> response = Map.of(
                "Service", "Bibliotech API",
                "Version", "0.1.0",
                "Status", "Running",
                "Timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);

    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = Map.of(
                "Status", "UP",
                "Timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

}
