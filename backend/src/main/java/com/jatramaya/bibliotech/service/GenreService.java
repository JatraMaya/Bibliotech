package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jatramaya.bibliotech.entity.book.GenreEntity;
import com.jatramaya.bibliotech.repository.GenreRepository;

@Service
public class GenreService {
    @Autowired
    private GenreRepository repo;

    public GenreEntity createNewGenre(String name) {

        String nameLowerCase = name.toLowerCase();
        if (repo.existsByname(nameLowerCase)) {
            throw new DataIntegrityViolationException("Genre already exist");
        }

        GenreEntity genre = new GenreEntity();
        genre.setName(nameLowerCase);

        return repo.save(genre);
    }

}
