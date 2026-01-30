package com.jatramaya.bibliotech.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.GenericWordDTO;
import com.jatramaya.bibliotech.dto.TagDTO;
import com.jatramaya.bibliotech.entity.book.GenreEntity;
import com.jatramaya.bibliotech.service.GenreService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        GenreEntity tag = service.getById(id);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Success get Tag data",
                "tag", new TagDTO(tag)));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        Page<TagDTO> pageData = service.getAll(page, size, sortBy, direction);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "data", pageData.getContent(),
                "currentPage", pageData.getNumber(),
                "totalItems", pageData.getTotalElements(),
                "totalPages", pageData.getTotalPages()));

    }

}
