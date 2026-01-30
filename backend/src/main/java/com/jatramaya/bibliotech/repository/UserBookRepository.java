package com.jatramaya.bibliotech.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jatramaya.bibliotech.entity.user.UserBookEntity;

@Repository
public interface UserBookRepository extends JpaRepository<UserBookEntity, Long> {

    Optional<UserBookEntity> findByUserIdAndBookId(UUID userId, Long bookId);

    boolean existsByUserIdAndBookId(UUID userId, Long bookId);
}
