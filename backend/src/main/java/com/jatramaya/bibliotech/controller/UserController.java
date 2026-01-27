package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.AddProfileDTO;
import com.jatramaya.bibliotech.dto.CurrentUserResponseDTO;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.service.AuthService;
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
    private UserService service;

    @Autowired
    private AuthService auth;

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponseDTO> getCurrentUser() {
        String username = auth.getCurrentUsername();
        UserEntity user = service.getCurrentUser(username);

        CurrentUserResponseDTO response = new CurrentUserResponseDTO(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<Map<String,String>> addProfile(@Valid @RequestBody
    AddProfileDTO dto ) {

    Aut
    }

}
