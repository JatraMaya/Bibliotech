package com.jatramaya.bibliotech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jatramaya.bibliotech.entity.book.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT DISTINCT b FROM BookEntity b JOIN b.authors a WHERE b.title = :title AND a.id = :authorId")
    Optional<BookEntity> findByTitleAndAuthor(@Param("title") String title, @Param("authorId") Long authorId);
}
