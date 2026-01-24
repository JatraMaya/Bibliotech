package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.exception.DuplicateCredentialException;
import com.jatramaya.bibliotech.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity register(UserEntity user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateCredentialException("Username of email already exist");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateCredentialException("Username of email already exist");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);

    }

}
