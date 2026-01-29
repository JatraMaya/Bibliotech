package com.jatramaya.bibliotech.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jatramaya.bibliotech.entity.book.TagEntity;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByName(String name);

    boolean existsByName(String name);

    Page<TagEntity> findAll(Pageable pageable);
}
