package com.jatramaya.bibliotech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @Size(min = 3, max = 10, message = "Username must be atleast 3 characters long")
    @NotBlank(message = "Username is required.")
    private String username;

    @Size(min = 3, max = 10, message = "First name must be at least three characters long.")
    @NotBlank(message = "First name is required.")
    private String firstname;

    @Size(min = 3, max = 10, message = "Last name must be at least three characters long.")
    private String lastname;

    @Size(min = 5, max = 100, message = "Email must be at least 5 characters long.")
    @Email(message = "Email must be a valid.")
    @NotBlank(message = "Email is required.")
    private String email;

    @Size(min = 6, max = 100, message = "Password must have at least 6 characters.")
    @NotBlank(message = "Password is required.")
    private String password;

}
