package com.jatramaya.bibliotech.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericWordDTO {

    @NotEmpty(message = "Value is required")
    @Size(min = 3, max = 25, message = "Value can be at least 3 characters long and max 25 characters long")
    private String name;
}
