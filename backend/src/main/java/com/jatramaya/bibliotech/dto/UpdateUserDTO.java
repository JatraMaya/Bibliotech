package com.jatramaya.bibliotech.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class UpdateUserDTO {

    private String username;

    @Email(regexp = ".+@.+\\..+", message = "Must be a valid email address")
    private String email;

    private String firstname;
    private String lastname;
    private String bio;

}
