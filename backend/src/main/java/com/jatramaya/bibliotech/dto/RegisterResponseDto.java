package com.jatramaya.bibliotech.dto;

import com.jatramaya.bibliotech.entity.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {
    private String username;
    private String fullname;
    private String email;

    public RegisterResponseDTO(UserEntity user) {
        setUsername(user.getUsername());
        setEmail(user.getEmail());

        if (user.getLastname() != null && !user.getLastname().isBlank()) {
            setFullname(user.getFirstname() + " " + user.getLastname());
        } else {
            setFullname(user.getFirstname());
        }
    }

}
