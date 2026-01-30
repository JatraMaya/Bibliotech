package com.jatramaya.bibliotech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jatramaya.bibliotech.dto.TagDTO;
import com.jatramaya.bibliotech.entity.book.TagEntity;
import com.jatramaya.bibliotech.exception.EntityNotFoundException;
import com.jatramaya.bibliotech.repository.TagRepository;

@Service
public class TagService {

    @Autowired
    private TagRepository repo;

    public TagEntity createNewTag(String name) {

        String tagLowerCase = name.toLowerCase();

        if (repo.existsByName(tagLowerCase))
            throw new DataIntegrityViolationException("Tag already exist");

        TagEntity tag = new TagEntity();
        tag.setName(tagLowerCase);
        return repo.save(tag);
    }

    public Page<TagDTO> getAll(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<TagEntity> entities = repo.findAll(pageable);
        return entities.map(TagDTO::new);
    }

    public TagEntity getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found"));
    }

    public void deleteTag(Long id) {
        TagEntity tag = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found"));

        repo.delete(tag);
    }
}
