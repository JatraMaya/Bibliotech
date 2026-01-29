package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.CurrentUserResponseDTO;
import com.jatramaya.bibliotech.dto.UpdateUserDTO;
import com.jatramaya.bibliotech.entity.user.ProfileEntity;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.service.AuthService;
import com.jatramaya.bibliotech.service.UserService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

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
    public ResponseEntity<Map<String, String>> deleteCurrentUser() {
        UserEntity user = getUser();

        service.deleteUserData(user.getUsername());

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "User and profile deleted"));

    }

}
