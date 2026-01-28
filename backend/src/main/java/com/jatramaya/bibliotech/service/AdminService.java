package com.jatramaya.bibliotech.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jatramaya.bibliotech.entity.user.Role;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository repo;

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
}
