package com.jatramaya.bibliotech.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class AddAuthorDTO {

    @Column(nullable = false)
    @Size(min = 3, max = 100, message = "Author's name must be at least 3 characters long.")
    @NotEmpty(message = "Auhor's name cannot be blank.")
    private String name;

    private String authorPictureUrl;

}
