package com.jatramaya.bibliotech.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.AddAuthorDTO;
import com.jatramaya.bibliotech.entity.book.AuthorEntity;
import com.jatramaya.bibliotech.service.AuthorService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> AddAuthor(@Valid @RequestBody AddAuthorDTO dto) {

        AuthorEntity author = service.createAuthor(dto);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Author add succesfully",
                "author", author));

    }
}