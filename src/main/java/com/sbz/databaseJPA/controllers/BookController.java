package com.sbz.databaseJPA.controllers;

import com.sbz.databaseJPA.domain.dto.BookDto;
import com.sbz.databaseJPA.domain.entities.Book;
import com.sbz.databaseJPA.mappers.Mapper;
import com.sbz.databaseJPA.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;
    private final Mapper<Book, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<Book, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }
    
    // This endpoint is used to create and update a book
    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        // Map BookDto to Book using mapper
        Book bookEntity = bookMapper.mapFrom(bookDto);

        // Asking if book exists or not before to perform .saveBook()
        boolean bookExists = bookService.isExists(isbn);

        // Update or create book
        Book savedBookEntity = bookService.saveBook(isbn, bookEntity);

        // Map Book to BookDto using mapper
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        // Check if the book exists or not and then create or update it
        if(bookExists){
            // Update book
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
        }
        // Create Book
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {
        List<Book> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn){
        Optional<Book> foundBook = bookService.findOne(isbn);
        return foundBook.map(bookEntity -> { // In case that a book exists we have bookEntity
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        if(!bookService.isExists(isbn))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Book bookEntity = bookMapper.mapFrom(bookDto);
        // Partial update
        Book updatedBook = bookService.partialUpdate(isbn, bookEntity);

        return new ResponseEntity<>(
                bookMapper.mapTo(updatedBook),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
