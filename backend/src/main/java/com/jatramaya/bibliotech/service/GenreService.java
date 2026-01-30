package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jatramaya.bibliotech.dto.TagDTO;
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

    public Page<TagDTO> getAll(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<GenreEntity> entities = repo.findAll(pageable);
        return entities.map(TagDTO::new);
    }

    public GenreEntity getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Genre not found"));
    }

    @Transactional
    public void deleteGenre(Long id) {
        GenreEntity tag = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found"));

        repo.delete(tag);
    }

}
