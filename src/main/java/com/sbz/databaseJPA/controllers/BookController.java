package com.sbz.databaseJPA.controllers;

import com.sbz.databaseJPA.domain.dto.BookDto;
import com.sbz.databaseJPA.domain.entities.Book;
import com.sbz.databaseJPA.mappers.Mapper;
import com.sbz.databaseJPA.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;
    private final Mapper<Book, BookDto> modelMapper;

    public BookController(BookService bookService, Mapper<Book, BookDto> modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto book
    ) {
        // Map BookDto to Book using mapper
        Book bookEntity = modelMapper.mapFrom(book);
        // Create Book
        Book savedBookEntity = bookService.createBook(isbn, bookEntity);
        // Map Book to BookDto using mapper
        BookDto savedBookDto = modelMapper.mapTo(savedBookEntity);
        
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {
        List<Book> books = bookService.findAll();
        return books.stream()
                .map(modelMapper::mapTo)
                .collect(Collectors.toList());
    }
}
