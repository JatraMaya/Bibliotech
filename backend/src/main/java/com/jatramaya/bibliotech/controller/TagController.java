package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.GenericWordDTO;
import com.jatramaya.bibliotech.dto.TagDTO;
import com.jatramaya.bibliotech.entity.book.TagEntity;
import com.jatramaya.bibliotech.service.TagService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService service;

    @PostMapping
    public ResponseEntity<?> createTag(@Valid @RequestBody GenericWordDTO dto) {

        String nameLowerCase = dto.getName().toLowerCase();

        TagEntity tag = service.createNewTag(nameLowerCase);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Success creating new tag",
                "tag", tag));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        TagEntity tag = service.getById(id);

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

    @DeleteMapping
    public ResponseEntity<?> deleteTag(@PathVariable Long id) {
        service.deleteTag(id);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Tag deleted"));
    }

}
