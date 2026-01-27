package com.jatramaya.bibliotech.dto;

import com.jatramaya.bibliotech.utils.validator.StrongCredential;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @StrongCredential
    @Size(min = 3, max = 10)
    @NotBlank(message = "Username is required.")
    private String username;

    @Size(min = 3, max = 10, message = "First name must be at least three characters long.")
    @NotBlank(message = "First name is required.")
    private String firstname;

    @Size(min = 0, max = 10, message = "Maximum character for lastname cannot be more then 10 characters long.")
    private String lastname;

    @Size(min = 5, max = 100)
    @Email(message = "Email must be a valid.")
    @NotBlank(message = "Email is required.")
    private String email;

    @StrongCredential(message = "Password must contain at least one number and one special character")
    @Size(min = 6, max = 100, message = "Password must have at least 6 characters.")
    @NotBlank(message = "Password is required.")
    private String password;

}
