package com.sbz.databaseJPA.services;

import com.sbz.databaseJPA.domain.entities.Author;

import java.util.List;

public interface AuthorService {
    Author createAuthor(Author author);

    List<Author> findAll();
}
