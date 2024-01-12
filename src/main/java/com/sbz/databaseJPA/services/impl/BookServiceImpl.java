package com.sbz.databaseJPA.services.impl;

import com.sbz.databaseJPA.domain.entities.Book;
import com.sbz.databaseJPA.repositories.BookRepository;
import com.sbz.databaseJPA.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveBook(String isbn, Book book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return StreamSupport
                .stream(
                        bookRepository.findAll().spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public Book partialUpdate(String isbn, Book bookEntity) {
        bookEntity.setIsbn(isbn);

        // Retrieve book from database and then updated it with bookEntity's data
        return bookRepository.findById(isbn).map(existingBook -> {

            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);

            // Save changes
            return bookRepository.save(existingBook);

        }).orElseThrow(() -> new RuntimeException("Book does not exist"));
    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
