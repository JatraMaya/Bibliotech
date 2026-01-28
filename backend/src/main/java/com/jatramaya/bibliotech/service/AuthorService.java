package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jatramaya.bibliotech.dto.AddAuthorDTO;
import com.jatramaya.bibliotech.entity.book.AuthorEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.AuthorRepo;

import static com.jatramaya.bibliotech.utils.Utility.hasValue;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepo repo;

    @Transactional
    public AuthorEntity createAuthor(AddAuthorDTO dto) {

        if (repo.existsByName(dto.getName().toLowerCase())) {
            throw new DataIntegrityViolationException("Author already exist");
        }

        AuthorEntity author = new AuthorEntity();

        if (hasValue(dto.getAuthorPictureUrl())) {
            author.setAuthorPictureUrl(dto.getAuthorPictureUrl());
        }

        author.setName(dto.getName().toLowerCase());

        return repo.save(author);
    }

    @Transactional
    public boolean deleteAuthor(Long id) {

        AuthorEntity author = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));

        repo.delete(author);

        return true;
    }

}
