package com.sbz.databaseJPA.services;

import com.sbz.databaseJPA.domain.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author saveAuthor(Author author);

    List<Author> findAll();

    Optional<Author> findOne(Long id);

    boolean isExists(Long id);
}
