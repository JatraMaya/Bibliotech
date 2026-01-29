package com.jatramaya.bibliotech.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jatramaya.bibliotech.dto.UserRegisterDTO;
import com.jatramaya.bibliotech.entity.user.Role;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity assingAdminAccess(UUID id) {
        UserEntity user = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setRole(Role.ADMIN);

        return repo.save(user);

    }

    @Transactional
    public UserEntity revokeAdminAccess(UUID id) {
        UserEntity user = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setRole(Role.USER);

        return repo.save(user);
    }

    @Transactional
    public UserEntity createAdmin(UserRegisterDTO dto) {
        UserEntity admin = new UserEntity();

        admin.setUsername(dto.getUsername());
        admin.setEmail(dto.getEmail());
        admin.setFirstname(dto.getFirstname());
        if (dto.getLastname() != null && !dto.getLastname().isBlank()) {
            admin.setLastname(dto.getLastname());
        }
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole(Role.ADMIN);

        return repo.save(admin);
    }
}
