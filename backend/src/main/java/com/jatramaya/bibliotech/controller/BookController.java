package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.CreateBookDTO;
import com.jatramaya.bibliotech.dto.CreateBookResponseDTO;
import com.jatramaya.bibliotech.entity.book.BookEntity;
import com.jatramaya.bibliotech.service.BookService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public ResponseEntity<?> createNewBook(
            @Valid @ModelAttribute CreateBookDTO dto,
            @RequestParam(required = false) MultipartFile cover) throws IOException {

        CreateBookResponseDTO book = service.createBook(dto, cover);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Add new Book succesfully",
                "book", book));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        Page<CreateBookResponseDTO> pageData = service.getAll(page, size, sortBy, direction);
        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "data", pageData.getContent(),
                "currentPage", pageData.getNumber(),
                "totalItems", pageData.getTotalElements(),
                "totalPages", pageData.getTotalPages()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id) {
        BookEntity book = service.getById(id);

        CreateBookResponseDTO response = new CreateBookResponseDTO(book);

        return ResponseEntity.ok(Map.of(
            "status", "Success",
            "message", "Succes get book data",
            "book", response
        ));
    }
    
}
