package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jatramaya.bibliotech.dto.UpdateUserDTO;
import com.jatramaya.bibliotech.entity.user.ProfileEntity;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    private boolean hasValue(String value) {
        return value != null && !value.isBlank();
    }

    public UserEntity getCurrentUser(String username) {

        return repo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public boolean updateUserProfile(UserEntity user, UpdateUserDTO dto) {

        ProfileEntity profile = user.getProfile();
        System.out.println(dto.getEmail());

        if (profile == null) {
            throw new EntityNotFoundException("Profile not found");
        }

        boolean updated = false;

        if (hasValue(dto.getUsername())) {
            user.setUsername(dto.getUsername());
            updated = true;
        }

        if (hasValue(dto.getEmail())) {
            user.setEmail(dto.getEmail());
            updated = true;
        }

        if (hasValue(dto.getFirstname())) {
            user.setFirstname(dto.getFirstname());
            updated = true;
        }

        if (hasValue(dto.getLastname())) {
            user.setLastname(dto.getLastname());
            updated = true;
        }

        if (hasValue(dto.getAvatarUrl())) {
            profile.setAvatarUrl(dto.getAvatarUrl());
            updated = true;
        }

        if (hasValue(dto.getBio())) {
            profile.setBio(dto.getBio());
            updated = true;
        }

        if (updated) {
            repo.save(user);
        }

        return updated;

    }

    @Transactional
    public boolean deleteUserData(String username) {

        UserEntity user = getCurrentUser(username);

        repo.delete(user);

        return true;

    }

}
