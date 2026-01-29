package com.jatramaya.bibliotech.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jatramaya.bibliotech.dto.RegisterResponseDTO;
import com.jatramaya.bibliotech.dto.UserRegisterDTO;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.service.AdminService;
import com.jatramaya.bibliotech.service.AuthService;
import com.jatramaya.bibliotech.service.AuthorService;
import com.jatramaya.bibliotech.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthorService authorService;

    AdminController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<Map<String, String>> deleteAuthor(@PathVariable Long id) {

        authorService.deleteAuthor(id);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Author data deleted"));

    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createNewAdmin(@Valid @RequestBody UserRegisterDTO dto) {

        UserEntity admin = adminService.createAdmin(dto);
        RegisterResponseDTO response = new RegisterResponseDTO(admin);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Sucessfully created new ADMIN user.",
                "admin", response));
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody UserRegisterDTO dto) {
        UserEntity newUser = authService.register(dto);
        RegisterResponseDTO response = new RegisterResponseDTO(newUser);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Success creating a new user",
                "user", response));

    }

    @PutMapping("/user/{id}/assign")
    public ResponseEntity<?> assingAdminFromUser(@PathVariable UUID id) {

        UserEntity newAdmin = adminService.assingAdminAccess(id);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Success Assign user with Admin privilage",
                "admin", Map.of(
                        "username", newAdmin.getUsername(),
                        "firstname", newAdmin.getFirstname(),
                        "email", newAdmin.getEmail(),
                        "role", newAdmin.getRole().name()

                )

        ));
    }

    @PutMapping("/user/{id}/revoke")
    public ResponseEntity<?> revokeAdminFromUser(@PathVariable UUID id) {

        UserEntity newAdmin = adminService.revokeAdminAccess(id);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "Success revoke admin access",
                "admin", Map.of(
                        "username", newAdmin.getUsername(),
                        "firstname", newAdmin.getFirstname(),
                        "email", newAdmin.getEmail(),
                        "role", newAdmin.getRole().name()

                )

        ));
    }

    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        userService.deleteUserDataById(id);

        return ResponseEntity.ok(Map.of(
                "status", "Success",
                "message", "User deleted successfully"));
    }

}
