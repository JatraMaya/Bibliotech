package com.jatramaya.bibliotech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jatramaya.bibliotech.entity.book.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

}
