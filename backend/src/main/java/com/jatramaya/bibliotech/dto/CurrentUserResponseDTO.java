package com.jatramaya.bibliotech.dto;

import com.jatramaya.bibliotech.entity.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserResponseDTO {

    private String username;
    private String email;
    private String fullname;
    private String avatarUrl;
    private String bio;

    public CurrentUserResponseDTO(UserEntity user) {

        setUsername(user.getUsername());
        setEmail(user.getEmail());

        if (user.getLastname() != null) {
            setFullname(user.getFirstname() + " " + user.getLastname());
        } else {
            setFullname(user.getFirstname());
        }

        if (user.getProfile() != null) {
            setAvatarUrl(user.getProfile().getAvatarUrl());
            setBio(user.getProfile().getBio());
        }

    }

}
