package com.jatramaya.bibliotech.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.AddAuthorDTO;
import com.jatramaya.bibliotech.dto.RegisterResponseDTO;
import com.jatramaya.bibliotech.dto.UserRegisterDTO;
import com.jatramaya.bibliotech.entity.book.AuthorEntity;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.service.AdminService;
import com.jatramaya.bibliotech.service.AuthService;
import com.jatramaya.bibliotech.service.AuthorService;
import com.jatramaya.bibliotech.service.GenreService;
import com.jatramaya.bibliotech.service.TagService;
import com.jatramaya.bibliotech.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        @Autowired
        private TagService tagService;

        @Autowired
        private GenreService genreService;

        AdminController(UserService userService) {
                this.userService = userService;
        }

        /// Author Related endpoint

        @PostMapping("/author")
        public ResponseEntity<?> createNewAuthor(
                        @Valid @ModelAttribute AddAuthorDTO dto,
                        @RequestParam(required = false) MultipartFile profilePic) throws IOException {

                AuthorEntity author = authorService.createAuthor(dto, profilePic);

                return ResponseEntity.ok(Map.of(
                                "status", "Success",
                                "message", "Crated new Author" + author.getName(),
                                "author", author));
        }

        @PutMapping("/author/{id}")
        public ResponseEntity<?> updateAuthor(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false) MultipartFile img,
                        @PathVariable Long id) throws IOException {
                boolean updated = authorService.updateAuthor(id, name, img);

                if (updated) {
                        return ResponseEntity.ok(Map.of(
                                        "status", "success",
                                        "message", "Successfully update author data"));
                }

                return ResponseEntity.ok(Map.of(
                                "status", "success",
                                "message", "author data not updated"));
        }

        @DeleteMapping("/author/{id}")
        public ResponseEntity<Map<String, String>> deleteAuthor(@PathVariable Long id) throws IOException {

                authorService.deleteAuthor(id);

                return ResponseEntity.ok(Map.of(
                                "status", "Success",
                                "message", "Author data deleted"));

        }

        @PutMapping("/author/img/{id}")
        public ResponseEntity<?> addAuthorPicture(
                        @PathVariable Long id, @RequestParam(required = true) MultipartFile image) throws IOException {

                AuthorEntity author = authorService.updateImg(id, image);

                return ResponseEntity.ok(Map.of(
                                "status", "Success",
                                "message", "Add profile image for author " + author.getName()));
        }

        @DeleteMapping("/author/img/{id}")
        public ResponseEntity<?> deleteAuthorImage(@PathVariable Long id) throws IOException {
                AuthorEntity author = authorService.deleteImg(id);

                return ResponseEntity.ok(Map.of(
                                "status", "Success",
                                "message", "Success deleting img profile for author " + author.getName()));
        }

        /// Role Realted endpoint

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

        /// User related endpoint

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
        public ResponseEntity<?> deleteUser(@PathVariable UUID id) throws IOException {
                userService.deleteUserDataById(id);

                return ResponseEntity.ok(Map.of(
                                "status", "Success",
                                "message", "User deleted successfully"));
        }

        // Tag and Genre Related endpoint
        @DeleteMapping("/tag/{id}")
        public ResponseEntity<?> deleteTag(@PathVariable Long id) {
                tagService.deleteTag(id);

                return ResponseEntity.ok(Map.of(
                                "status", "success",
                                "message", "Tag deleted"));
        }

        @DeleteMapping("/genre/{id}")
        public ResponseEntity<?> deleteGenre(@PathVariable Long id) {
                genreService.deleteGenre(id);

                return ResponseEntity.ok(Map.of(
                                "status", "success",
                                "message", "Tag deleted"));
        }

}
