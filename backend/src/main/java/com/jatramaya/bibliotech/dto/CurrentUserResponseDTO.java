package com.jatramaya.bibliotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserResponseDTO {

    private String username;
    private String email;
    private String firstname;
    private String fullname;
    private String avatarUrl;
    private String bio;

}
