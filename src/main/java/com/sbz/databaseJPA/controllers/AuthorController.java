package com.sbz.databaseJPA.controllers;

import com.sbz.databaseJPA.domain.dto.AuthorDto;
import com.sbz.databaseJPA.domain.entities.Author;
import com.sbz.databaseJPA.mappers.Mapper;
import com.sbz.databaseJPA.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<Author, AuthorDto> authorMapper;

    @Autowired
    public AuthorController(final AuthorService authorService, final Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        // Map AuthorDto to Author using mapper
        Author authorEntity = authorMapper.mapFrom(author);
        // Create Author
        Author savedAuthorEntity = authorService.saveAuthor(authorEntity);
        // Map Author to AuthorDto using mapper
        AuthorDto savedAuthorDto = authorMapper.mapTo(savedAuthorEntity);

        return new ResponseEntity<>(savedAuthorDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors() {
        List<Author> authors = authorService.findAll();
        /*
        List<AuthorDto> authorsDto = new ArrayList<AuthorDto>();
        for(Author author : authors){
            authorsDto.add(authorMapper.mapTo(author));
        }
        return authorsDto;
        */
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<Author> foundAuthor = authorService.findOne(id);
        // Look at this (I must search about this)
        return foundAuthor.map(authorEntity -> { // In case that an author exists we have authorEntity
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    ) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Set id (It can be better in authorService)
        authorDto.setId(id);
        Author authorEntity = authorMapper.mapFrom(authorDto);
        // Update author using the save method
        Author savedAuthorEntity = authorService.saveAuthor(authorEntity);
        return new ResponseEntity<>(
                authorMapper.mapTo(savedAuthorEntity),
                HttpStatus.OK
        );
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    ) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Author authorEntity = authorMapper.mapFrom(authorDto);
        // Partial update
        Author updatedAuthor = authorService.partialUpdate(id, authorEntity);

        return new ResponseEntity<>(
                authorMapper.mapTo(updatedAuthor),
                HttpStatus.OK
        );
    }
}
