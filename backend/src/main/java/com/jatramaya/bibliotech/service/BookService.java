package com.jatramaya.bibliotech.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.CreateBookDTO;
import com.jatramaya.bibliotech.dto.CreateBookResponseDTO;
import com.jatramaya.bibliotech.entity.book.AuthorEntity;
import com.jatramaya.bibliotech.entity.book.BookEntity;
import com.jatramaya.bibliotech.entity.book.GenreEntity;
import com.jatramaya.bibliotech.entity.book.TagEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.AuthorRepo;
import com.jatramaya.bibliotech.repository.BookRepository;
import com.jatramaya.bibliotech.repository.GenreRepository;
import com.jatramaya.bibliotech.repository.TagRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private GenreRepository genreRepo;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private ImageService imgService;

    @Transactional
    public CreateBookResponseDTO createBook(CreateBookDTO dto, MultipartFile img) throws IOException {

        boolean hasImgCover = img != null && !img.isEmpty();

        for (Long authorId : dto.getAuthorsId()) {
            Optional<BookEntity> existingBook = repo.findByTitleAndAuthor(dto.getTitle().toLowerCase(), authorId);
            if (existingBook.isPresent())
                throw new DataIntegrityViolationException("Book title by the same authors already exist");
        }

        BookEntity newBook = new BookEntity();
        newBook.setTitle(dto.getTitle().toLowerCase());
        if (hasImgCover) {
            String coverUrl = imgService.uploadAvatar(img);
            newBook.setCoverUrl(coverUrl);
        }

        Set<AuthorEntity> authors = new HashSet<>();
        for (Long authorId : dto.getAuthorsId()) {
            AuthorEntity author = authorRepo.findById(authorId)
                    .orElseThrow(() -> new EntityNotFoundException("Author not found"));
            authors.add(author);
        }
        newBook.setAuthors(authors);

        Set<GenreEntity> genres = new HashSet<>();
        for (Long genreId : dto.getGenresId()) {
            GenreEntity genre = genreRepo.findById(genreId)
                    .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + genreId));
            genres.add(genre);
        }
        newBook.setGenres(genres);

        if (dto.getTagsId() != null && !dto.getTagsId().isEmpty()) {

            Set<TagEntity> tags = new HashSet<>();
            for (Long tagId : dto.getTagsId()) {
                TagEntity tag = tagRepo.findById(tagId)
                        .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId));
                tags.add(tag);
            }
            newBook.setTags(tags);
        }

        BookEntity book = repo.save(newBook);
        repo.flush();

        return new CreateBookResponseDTO(book);

    }

    @Transactional(readOnly = true)
    public Page<CreateBookResponseDTO> getAll(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<BookEntity> entities = repo.findAll(pageable);
        return entities.map(CreateBookResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public CreateBookResponseDTO getById(Long id) {
        BookEntity book = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        return new CreateBookResponseDTO(book);
    }

    @Transactional(readOnly = true)
    public Page<CreateBookResponseDTO> searchBooks(
            List<Long> authorIds,
            List<Long> genreIds,
            List<Long> tagIds,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> books = repo.searchBooks(authorIds, genreIds, tagIds, pageable);

        return books.map(book -> {
            book.getAuthors().size();
            book.getGenres().size();
            book.getTags().size();
            return new CreateBookResponseDTO(book);
        });
    }

    @Transactional
    public void deleteBook(Long Id) throws IOException {
        BookEntity book = repo.findById(Id).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (book.getCoverUrl() != null) {
            imgService.deleteAvatar(book.getCoverUrl());
        }

        repo.delete(book);
    }

}
