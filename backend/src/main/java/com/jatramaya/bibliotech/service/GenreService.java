package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jatramaya.bibliotech.entity.book.GenreEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.GenreRepository;

@Service
public class GenreService {
    @Autowired
    private GenreRepository repo;

    @Transactional
    public GenreEntity createNewGenre(String name) {

        String nameLowerCase = name.toLowerCase();
        if (repo.existsByname(nameLowerCase)) {
            throw new DataIntegrityViolationException("Genre already exist");
        }

        GenreEntity genre = new GenreEntity();
        genre.setName(nameLowerCase);

        return repo.save(genre);
    }

    public void deleteGenre(Long id) {
        GenreEntity tag = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found"));

        repo.delete(tag);
    }

}
