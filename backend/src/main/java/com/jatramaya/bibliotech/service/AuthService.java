package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.exception.DuplicateCredentialException;
import com.jatramaya.bibliotech.exception.InvalidPasswordException;
import com.jatramaya.bibliotech.repository.UserRepository;
import com.jatramaya.bibliotech.utils.JWT;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWT jwt;

    public UserEntity register(UserEntity user) {

        if (repo.existsByUsername(user.getUsername())) {
            throw new DuplicateCredentialException("Username or email already exist");
        }
        if (repo.existsByEmail(user.getEmail())) {
            throw new DuplicateCredentialException("Username or email already exist");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repo.save(user);

    }

    public String login(String username, String password) {
        UserEntity user = repo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Username not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Unauthorized password");
        }

        return jwt.generateToken(user.getUsername(), user.getRole().name());
    }

}
