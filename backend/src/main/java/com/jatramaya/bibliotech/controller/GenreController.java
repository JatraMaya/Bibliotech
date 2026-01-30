package com.jatramaya.bibliotech.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.GenericWordDTO;
import com.jatramaya.bibliotech.entity.book.GenreEntity;
import com.jatramaya.bibliotech.service.GenreService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private GenreService service;

    @PostMapping
    public ResponseEntity<?> createGenre(@Valid @RequestBody GenericWordDTO dto) {

        String name = dto.getName().toLowerCase();
        GenreEntity genre = service.createNewGenre(name);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Add new genre: " + genre.getName()));
    }

}
