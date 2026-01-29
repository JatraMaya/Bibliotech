package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.TagDTO;
import com.jatramaya.bibliotech.entity.book.TagEntity;
import com.jatramaya.bibliotech.service.TagService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService service;

    @PostMapping
    public ResponseEntity<?> createTag(@RequestParam(required = true) String tagName) {
        TagEntity tag = service.createNewTag(tagName);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Success creating new tag",
                "tag", tag));
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
