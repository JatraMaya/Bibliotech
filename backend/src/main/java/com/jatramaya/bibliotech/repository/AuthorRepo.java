package com.jatramaya.bibliotech.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jatramaya.bibliotech.entity.book.AuthorEntity;

@Repository
public interface AuthorRepo extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findById(Long id);

    Optional<AuthorEntity> findByName(String name);

    boolean existsByName(String name);

    Page<AuthorEntity> findAll(Pageable pageable);

}
