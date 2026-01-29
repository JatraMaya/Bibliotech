package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.UpdateUserDTO;
import com.jatramaya.bibliotech.entity.user.ProfileEntity;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.ProfileRepository;
import com.jatramaya.bibliotech.repository.UserRepository;
import static com.jatramaya.bibliotech.utils.Utility.hasValue;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private ProfileRepository profileRepo;

    @Autowired
    private FileUploadService uploadService;

    public UserEntity getCurrentUser(String username) {

        return repo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public ProfileEntity createProfile(UserEntity user, String bio, MultipartFile avatar) throws IOException {

        if (profileRepo.existsByUser(user))
            throw new DataIntegrityViolationException("User profile already exist");

        boolean hasAvatar = avatar != null && !avatar.isEmpty();
        boolean hasBio = bio != null && !bio.isBlank();

        if (!hasAvatar && !hasBio) {
            return null;
        }

        ProfileEntity profile = new ProfileEntity();
        profile.setUser(user);
        if (hasAvatar) {
            String avatarUrl = uploadService.uploadAvatar(avatar);
            profile.setAvatarUrl(avatarUrl);
        }

        if (hasBio)
            profile.setBio(bio);

        return profileRepo.save(profile);
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

    @Transactional
    public boolean deleteUserDataById(UUID id) {

        UserEntity user = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        repo.delete(user);

        return true;

    }

}
