package com.jatramaya.bibliotech.dto;

import com.jatramaya.bibliotech.entity.book.AuthorEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAuthorResponseDTO {

    private String name;
    private String authorPictureUrl;

    public AddAuthorResponseDTO(AuthorEntity entity) {
        name = entity.getName();
        authorPictureUrl = entity.getAuthorPicturUrl();
    }

}
