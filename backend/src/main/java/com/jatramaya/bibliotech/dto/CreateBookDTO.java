package com.jatramaya.bibliotech.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookDTO {

    @Column(nullable = false)
    @Size(min = 5, max = 100, message = "Book title must be at least 5 characters long.")
    @NotBlank(message = "Book title cannot be blank")
    private String title;

    @NotEmpty(message = "Book required at least one author")
    private List<Long> authorsId;

    @NotEmpty(message = "Book required at least one genre")
    private List<Long> genresId;

    private List<Long> tagsId;
}
