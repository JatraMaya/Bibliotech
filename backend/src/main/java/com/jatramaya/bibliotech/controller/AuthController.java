package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.RegisterResponseDto;
import com.jatramaya.bibliotech.dto.UserRegisterDto;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.service.AuthService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserRegisterDto dto) {
        UserEntity user = new UserEntity();

        user.setUsername(dto.getUsername());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());

        UserEntity saved = authService.register(user);

        String fullname;

        if (!saved.getLastname().isEmpty()) {
            fullname = saved.getFirstname() + " " + saved.getLastname();
        } else {
            fullname = saved.getFirstname();
        }
        RegisterResponseDto result = new RegisterResponseDto();
        result.setUsername(saved.getUsername());
        result.setEmail(saved.getEmail());
        result.setFullname(fullname);

        Map<String, Object> response = Map.of(
                "status", "success",
                "message", "user created successfully",
                "user", result);

        return ResponseEntity.ok(response);
    }

}
