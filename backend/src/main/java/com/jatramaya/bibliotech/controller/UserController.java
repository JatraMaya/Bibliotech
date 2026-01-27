package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.AddProfileDTO;
import com.jatramaya.bibliotech.dto.CurrentUserResponseDTO;
import com.jatramaya.bibliotech.entity.user.ProfileEntity;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.service.AuthService;
import com.jatramaya.bibliotech.service.ProfileService;
import com.jatramaya.bibliotech.service.UserService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService auth;

    private UserEntity getUser() {
        String username = auth.getCurrentUsername();
        return userService.getCurrentUser(username);
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponseDTO> getCurrentUser() {

        CurrentUserResponseDTO response = new CurrentUserResponseDTO(getUser());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<Map<String, String>> addProfile(@Valid @RequestBody AddProfileDTO dto) {

        UserEntity user = getUser();
        ProfileEntity profile = profileService.createProfile(user, dto);
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

}
