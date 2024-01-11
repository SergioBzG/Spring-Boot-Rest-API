package com.sbz.databaseJPA.services.impl;

import com.sbz.databaseJPA.domain.entities.Author;
import com.sbz.databaseJPA.repositories.AuthorRepository;
import com.sbz.databaseJPA.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(Author author){
        return authorRepository.save(author);
    }
}
