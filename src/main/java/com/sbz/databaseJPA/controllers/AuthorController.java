package com.sbz.databaseJPA.controllers;

import com.sbz.databaseJPA.domain.dto.AuthorDto;
import com.sbz.databaseJPA.domain.entities.Author;
import com.sbz.databaseJPA.mappers.Mapper;
import com.sbz.databaseJPA.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<Author, AuthorDto> authorMapper;

    @Autowired
    public AuthorController(final AuthorService authorService, final Mapper<Author, AuthorDto> authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        // Map AuthorDto to Author using mapper
        Author authorEntity = authorMapper.mapFrom(author);
        Author savedAuthorEntity = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }
}
