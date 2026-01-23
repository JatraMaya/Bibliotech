package com.jatramaya.bibliotech.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 10)
    @Size(min = 3, max = 10, message = "Username must be atleast 3 characters long")
    @NotBlank(message = "Username is required.")
    private String username;

    @Column(nullable = false, length = 10)
    @Size(min = 3, max = 10, message = "First name must be at least three characters long.")
    @NotBlank(message = "First name is required.")
    private String firstname;

    @Column(nullable = true, length = 10)
    @Size(min = 3, max = 10, message = "Last name must be at least three characters long.")
    private String lastname;

    @Column(nullable = false, length = 100, unique = true)
    @Size(min = 5, max = 100, message = "Email must be at least 5 characters long.")
    @Email(message = "Email must be a valid.")
    @NotBlank(message = "Email is required.")
    private String email;

    @Column(nullable = false, length = 100)
    @Size(min = 6, max = 100, message = "Password must have at least 6 characters.")
    @NotBlank(message = "Password is required.")
    private String hashPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfileEntity profile;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
