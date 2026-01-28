package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jatramaya.bibliotech.dto.ChangePasswordDTO;
import com.jatramaya.bibliotech.dto.UserLoginDTO;
import com.jatramaya.bibliotech.dto.UserRegisterDTO;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.exception.DuplicateCredentialException;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.exception.InvalidPasswordException;
import com.jatramaya.bibliotech.repository.UserRepository;
import com.jatramaya.bibliotech.utils.JWT;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWT jwt;

    private String notFoundError = "User not found";

    @Transactional
    public UserEntity register(UserRegisterDTO dto) {
        UserEntity user = new UserEntity();

        String errorResponse = "Username or email already exist";

        user.setUsername(dto.getUsername().toLowerCase());
        user.setFirstname(dto.getFirstname().toLowerCase());
        user.setLastname(dto.getLastname().toLowerCase());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail().toLowerCase());

        if (repo.existsByUsername(user.getUsername())) {
            throw new DuplicateCredentialException(errorResponse);
        }
        if (repo.existsByEmail(user.getEmail())) {
            throw new DuplicateCredentialException(errorResponse);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repo.save(user);

    }

    public String login(UserLoginDTO dto) {
        UserEntity user = repo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(notFoundError));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Unauthorized password");
        }

        return jwt.generateToken(user.getUsername(), user.getRole().name());
    }

    @Transactional
    public void changePassword(ChangePasswordDTO dto) {

        UserEntity user = repo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(notFoundError));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password not match");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        repo.save(user);

    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}
