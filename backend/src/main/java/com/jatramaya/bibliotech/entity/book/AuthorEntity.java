package com.jatramaya.bibliotech.entity.book;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authors", indexes = {
    @Index(name = "idx_authorName", columnList = "name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 100, message = "Author's name must be at least 3 characters long.")
    @NotBlank(message = "Auhor's name cannot be blank.")
    private String name;

    @ManyToMany(mappedBy = "authors")
    private Set<BookEntity> books = new HashSet<>();

    private String authorePictureUrl;
}
