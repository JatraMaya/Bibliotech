package com.jatramaya.bibliotech.dto;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookDTO {

    @Size(min = 5, max = 100, message = "Book title must be at least 5 characters long.")
    private String title;
    private List<Long> authorsId;
    private List<Long> genresId;

    private List<Long> tagsId;
}
