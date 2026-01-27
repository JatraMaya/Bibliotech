package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jatramaya.bibliotech.dto.AddProfileDTO;
import com.jatramaya.bibliotech.entity.user.ProfileEntity;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository repo;

    public ProfileEntity createProfile(UserEntity user, AddProfileDTO dto) {

        if (repo.existsByUser(user))
            throw new DataIntegrityViolationException("User profile already exist");

        boolean hasAvatar = dto.getAvatarUrl() != null && !dto.getAvatarUrl().isBlank();
        boolean hasBio = dto.getBio() != null && !dto.getBio().isBlank();

        if (!hasAvatar && !hasBio) {
            return null;
        }

        ProfileEntity profile = new ProfileEntity();
        profile.setUser(user);
        if (hasAvatar)
            profile.setAvatarUrl(dto.getAvatarUrl());

        if (hasBio)
            profile.setBio(dto.getBio());

        return repo.save(profile);
    }

}
