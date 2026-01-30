package com.jatramaya.bibliotech.dto;

import com.jatramaya.bibliotech.entity.book.BookStatus;
import com.jatramaya.bibliotech.entity.user.UserBookEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCollectionDTO {

    private String bookTitle;
    private String comment;
    private BookStatus readingStatus;

    public BookCollectionDTO(UserBookEntity book) {
        bookTitle = book.getBook().getTitle();
        comment = book.getComment();
        readingStatus = book.getReadingStatus();
    }
}
