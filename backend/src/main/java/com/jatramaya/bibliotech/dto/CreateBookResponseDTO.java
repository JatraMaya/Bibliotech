package com.jatramaya.bibliotech.dto;

import java.util.HashSet;
import java.util.Set;

import com.jatramaya.bibliotech.entity.book.BookEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookResponseDTO {

        private Long id;
        private String title;
        private String coverUrl;
        private Set<AddAuthorResponseDTO> authors;
        private Set<GenericDTOResponse> genres;
        private Set<GenericDTOResponse> tags;

        public CreateBookResponseDTO(BookEntity book) {
                id = book.getId();
                title = book.getTitle();

                authors = new HashSet<>(
                                book.getAuthors()
                                                .stream()
                                                .map(AddAuthorResponseDTO::new)
                                                .toList());

                genres = new HashSet<>(
                                book.getGenres()
                                                .stream()
                                                .map(g -> new GenericDTOResponse(g.getId(), g.getName()))
                                                .toList());

                tags = new HashSet<>(
                                book.getTags()
                                                .stream()
                                                .map(t -> new GenericDTOResponse(t.getId(), t.getName()))
                                                .toList());
                coverUrl = book.getCoverUrl();
        }
}
