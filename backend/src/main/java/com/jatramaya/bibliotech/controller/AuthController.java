package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.ChangePasswordDTO;
import com.jatramaya.bibliotech.dto.RegisterResponseDTO;
import com.jatramaya.bibliotech.dto.UserLoginDTO;
import com.jatramaya.bibliotech.dto.UserRegisterDTO;
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
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserRegisterDTO dto) {

        UserEntity user = authService.register(dto);
        RegisterResponseDTO result = new RegisterResponseDTO(user);

        Map<String, Object> response = Map.of(
                "status", "success",
                "message", "user created successfully",
                "user", result);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserLoginDTO dto) {
        String token = authService.login(dto.getUsername(), dto.getPassword());
        Map<String, String> response = Map.of("Status", "Succes", "Message", "Login success", "Token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password")
    public ResponseEntity<Map<String, String>> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {

        authService.changePassword(dto.getUsername(), dto.getCurrentPassword(), dto.getNewPassword());

        Map<String, String> response = Map.of("status", "Success", "message", "Password updated succesfully");

        return ResponseEntity.ok(response);
    }

}
