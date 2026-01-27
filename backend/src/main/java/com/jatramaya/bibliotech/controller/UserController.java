package com.jatramaya.bibliotech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.CurrentUserResponseDTO;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponseDTO> getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String fullname;

        UserEntity user = service.getCurrentUser(username);
        if (user.getLastname() != null) {
            fullname = user.getFirstname() + " " + user.getLastname();
        } else {
            fullname = user.getFirstname();
        }
        CurrentUserResponseDTO response = new CurrentUserResponseDTO();
        response.setUsername(user.getUsername());
        response.setFullname(fullname);
        response.setEmail(user.getEmail());

        if (user.getProfile() != null) {
            response.setAvatarUrl(user.getProfile().getAvatarUrl());
            response.setBio(user.getProfile().getBio());
        }

        return ResponseEntity.ok(response);
    }

}
