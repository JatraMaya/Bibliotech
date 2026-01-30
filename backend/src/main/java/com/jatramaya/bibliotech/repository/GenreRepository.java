package com.jatramaya.bibliotech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jatramaya.bibliotech.entity.book.GenreEntity;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    boolean existsByname(String name);

}
