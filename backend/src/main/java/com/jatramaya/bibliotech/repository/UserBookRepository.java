package com.jatramaya.bibliotech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jatramaya.bibliotech.entity.user.UserBookEntity;

@Repository
public interface UserBookRepository extends JpaRepository<UserBookEntity, Long> {

}
