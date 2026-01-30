package com.jatramaya.bibliotech.service;

import static com.jatramaya.bibliotech.utils.Utility.hasValue;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jatramaya.bibliotech.dto.AddBookCollectionDTO;
import com.jatramaya.bibliotech.entity.book.BookEntity;
import com.jatramaya.bibliotech.entity.user.UserBookEntity;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.BookRepository;
import com.jatramaya.bibliotech.repository.UserBookRepository;
import com.jatramaya.bibliotech.repository.UserRepository;

@Service
public class UserBookService {

    @Autowired
    private UserBookRepository repo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public UserBookEntity addBookCollection(AddBookCollectionDTO dto, UUID userId) {

        if (repo.existsByUserIdAndBookId(userId, dto.getBookId())) {
            throw new DataIntegrityViolationException("Book already listed in user collection");
        }

        if (dto.getComment() != null && !dto.getComment().isBlank() && dto.getComment().length() < 10) {
            throw new IllegalArgumentException("Comment must be at least 10 characters long");
        }

        UserBookEntity collection = new UserBookEntity();
        BookEntity book = bookRepo.findById(dto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        collection.setBook(book);
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        collection.setUser(user);
        collection.setComment(dto.getComment());
        if (hasValue(dto.getReadingStatus().name())) {
            collection.setReadingStatus(dto.getReadingStatus());
        }

        return repo.save(collection);
    }
}
