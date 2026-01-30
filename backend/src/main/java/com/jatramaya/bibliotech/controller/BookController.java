package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.CreateBookDTO;
import com.jatramaya.bibliotech.dto.CreateBookResponseDTO;
import com.jatramaya.bibliotech.service.BookService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
