package com.jatramaya.bibliotech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jatramaya.bibliotech.entity.book.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT DISTINCT b FROM BookEntity b JOIN b.authors a WHERE b.title = :title AND a.id = :authorId")
    Optional<BookEntity> findByTitleAndAuthor(@Param("title") String title, @Param("authorId") Long authorId);

    @Query("""
            SELECT DISTINCT b FROM BookEntity b
            LEFT JOIN b.authors a
            LEFT JOIN b.genres g
            LEFT JOIN b.tags t
            WHERE (:authorIds IS NULL OR a.id IN :authorIds)
            AND (:genreIds IS NULL OR g.id IN :genreIds)
            AND (:tagIds IS NULL OR t.id IN :tagIds)
            """)
    Page<BookEntity> searchBooks(
            @Param("authorIds") List<Long> authorIds,
            @Param("genreIds") List<Long> genreIds,
            @Param("tagIds") List<Long> tagIds,
            Pageable pageable);

    Page<BookEntity> findAll(Pageable pageable);
}
