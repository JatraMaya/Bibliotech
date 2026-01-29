package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jatramaya.bibliotech.dto.AddAuthorDTO;
import com.jatramaya.bibliotech.entity.book.AuthorEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.AuthorRepo;

import java.io.IOException;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepo repo;

    @Autowired
    private ImageService imageService;

    @Transactional
    public AuthorEntity createAuthor(AddAuthorDTO dto, MultipartFile file) throws IOException {

        boolean hasProfilePicture = file != null && !file.isEmpty();

        if (repo.existsByName(dto.getName().toLowerCase())) {
            throw new DataIntegrityViolationException("Author already exist");
        }

        AuthorEntity author = new AuthorEntity();

        if (hasProfilePicture) {
            String profilePicUrl = imageService.uploadAvatar(file);
            author.setAuthorPicturUrl(profilePicUrl);
        }

        author.setName(dto.getName().toLowerCase());

        return repo.save(author);
    }

    public AuthorEntity getAuthorById(Long id) {
        AuthorEntity author = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));

        return author;
    }

    @Transactional
    public boolean updateAuthor(Long id, String name, MultipartFile img) throws IOException {

        boolean updated = false;

        AuthorEntity author = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));

        if (name != null) {
            author.setName(name);
            updated = true;
        }

        if (img != null) {
            imageService.deleteAvatar(author.getAuthorPicturUrl());
            imageService.uploadAvatar(img);
            updated = true;
        }

        if (updated) {
            repo.save(author);
        }

        return updated;

    }

    @Transactional
    public boolean deleteAuthor(Long id) throws IOException {

        AuthorEntity author = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));

        imageService.deleteAvatar(author.getAuthorPicturUrl());
        repo.delete(author);

        return true;
    }

    @Transactional
    public AuthorEntity updateImg(Long id, MultipartFile img) throws IOException {
        AuthorEntity author = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));

        if (author.getAuthorPicturUrl() != null) {
            imageService.deleteAvatar(author.getAuthorPicturUrl());
        }

        String profilePicUrl = imageService.uploadAvatar(img);

        author.setAuthorPicturUrl(profilePicUrl);

        return repo.save(author);
    }

    @Transactional
    public AuthorEntity deleteImg(Long id) throws IOException {
        AuthorEntity author = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));

        if (author.getAuthorPicturUrl() == null) {
            throw new EntityNotFoundException("No image found for selected author");
        }

        imageService.deleteAvatar(author.getAuthorPicturUrl());
        author.setAuthorPicturUrl(null);
        return repo.save(author);
    }

}
