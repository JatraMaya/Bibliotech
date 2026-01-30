package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.AddBookCollectionDTO;
import com.jatramaya.bibliotech.dto.BookCollectionDTO;
import com.jatramaya.bibliotech.dto.CurrentUserResponseDTO;
import com.jatramaya.bibliotech.dto.ReviewDTO;
import com.jatramaya.bibliotech.dto.UpdateUserDTO;
import com.jatramaya.bibliotech.entity.book.BookStatus;
import com.jatramaya.bibliotech.entity.user.ProfileEntity;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.service.AuthService;
import com.jatramaya.bibliotech.service.ImageService;
import com.jatramaya.bibliotech.service.UserBookService;
import com.jatramaya.bibliotech.service.UserService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserBookService userBookService;

    @Autowired
    private AuthService auth;

    private UserEntity getUser() {
        String username = auth.getCurrentUsername();
        return service.getCurrentUser(username);
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponseDTO> getCurrentUser() {

        CurrentUserResponseDTO response = new CurrentUserResponseDTO(getUser());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<Map<String, String>> addProfile(
            @RequestParam(required = false) String bio,
            @RequestParam(required = false) MultipartFile avatar) throws IOException {

        UserEntity user = getUser();
        ProfileEntity profile = service.createProfile(user, bio, avatar);
        Map<String, String> result;

        if (profile == null) {
            result = Map.of(
                    "status", "Success",
                    "message", "No profile updated");
        } else {
            result = Map.of(
                    "status", "Success",
                    "message", "Profile Created");
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/profile")
    public ResponseEntity<Map<String, String>> updateUser(
            @Valid @ModelAttribute UpdateUserDTO dto,
            @RequestParam(required = false) MultipartFile avatar) throws IOException {

        UserEntity user = getUser();
        boolean dataUpdated = service.updateUserProfile(user, dto, avatar);

        Map<String, String> result;

        if (dataUpdated) {
            result = Map.of("status", "Success", "message", "Profile Updated");
        } else {
            result = Map.of("status", "Success", "message", "Profile not updated");
        }

        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/profile")
    public ResponseEntity<Map<String, String>> deleteCurrentUser() throws IOException {
        UserEntity user = getUser();

        service.deleteUserData(user.getUsername());

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Account Deleted"));

    }

    @GetMapping("/profile/avatar")
    public ResponseEntity<Resource> getAvatar() throws IOException {
        UserEntity user = getUser();
        Resource resource = imageService.getAvatar(user);
        String contentType = imageService.getContentType(user.getProfile().getAvatarUrl());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);

    }

    @PostMapping("/book")
    public ResponseEntity<?> addCollection(
            @Valid @RequestBody AddBookCollectionDTO dto) {

        userBookService.addBookCollection(dto, getUser().getId());

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Succefully add book to collection"));
    }

    @PutMapping("/book/{id}/status")
    public ResponseEntity<?> updateReadingStatus(@PathVariable Long id, @RequestParam BookStatus status) {

        boolean updated = userBookService.updateReadingStatus(id, status);

        if (updated) {
            return ResponseEntity.ok(Map.of(
                    "status", "Success",
                    "message", "Update reading status to: " + status.name()));
        }

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Reading status not updated"));

    }

    @PutMapping("/book/{id}/comment")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody ReviewDTO dto) {

        if (dto.getReview() == null) {
            throw new IllegalArgumentException("Missing updated review");
        }
        boolean updated = userBookService.updateComment(id, dto.getReview().toLowerCase());

        if (updated) {

            return ResponseEntity.ok(Map.of(
                    "status", "Success",
                    "message", "Update book review"));

        }

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Book Review not updated"));

    }

    @GetMapping("/book/all")
    public ResponseEntity<?> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BookCollectionDTO> books = userBookService.getAllCollection(getUser().getId(), page, size);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "data", books.getContent(),
                "currentPage", books.getNumber(),
                "totalItems", books.getTotalElements(),
                "totalPages", books.getTotalPages()));

    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> removeFromCollection(@PathVariable Long id) {
        userBookService.removeFromCollection(id);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Removed book from collection"));
    }
}
