package com.jatramaya.bibliotech.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.AddAuthorDTO;
import com.jatramaya.bibliotech.entity.book.AuthorEntity;
import com.jatramaya.bibliotech.service.AuthorService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> AddAuthor(
            @Valid @ModelAttribute AddAuthorDTO dto,
            @RequestParam(required = false) MultipartFile profilePic) throws IOException {

        AuthorEntity author = service.createAuthor(dto, profilePic);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Author add succesfully",
                "author", author));

    }
}