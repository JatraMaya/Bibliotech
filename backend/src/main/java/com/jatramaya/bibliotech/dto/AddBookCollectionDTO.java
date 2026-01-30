package com.jatramaya.bibliotech.dto;

import com.jatramaya.bibliotech.entity.book.BookStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBookCollectionDTO {

    private String comment;

    @NotNull(message = "must have provide bookId")
    private Long bookId;

    private BookStatus readingStatus;
}
