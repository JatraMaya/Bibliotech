package com.jatramaya.bibliotech.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.AddAuthorDTO;
import com.jatramaya.bibliotech.dto.AddAuthorResponseDTO;
import com.jatramaya.bibliotech.entity.book.AuthorEntity;
import com.jatramaya.bibliotech.service.AuthorService;
import com.jatramaya.bibliotech.service.ImageService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService service;

    @Autowired
    private ImageService imageService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        Page<AddAuthorResponseDTO> pageData = service.getAll(page, size, sortBy, direction);
        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "data", pageData.getContent(),
                "currentPage", pageData.getNumber(),
                "totalItems", pageData.getTotalElements(),
                "totalPages", pageData.getTotalPages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorbyId(@PathVariable Long id) {

        AuthorEntity author = service.getAuthorById(id);
        AddAuthorResponseDTO response = new AddAuthorResponseDTO(author.getName(), author.getAuthorPicturUrl());
        System.out.println("INI ID " + id);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Succesfully get author data",
                "author", response));
    }

    @GetMapping("/img/{filename}")
    public ResponseEntity<Resource> getAuthorImage(@PathVariable String filename) throws IOException {

        Resource resource = imageService.getImage(filename);
        String contentType = imageService.getContentType(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);

    }

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