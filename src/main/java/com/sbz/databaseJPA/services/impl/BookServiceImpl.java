package com.sbz.databaseJPA.services.impl;

import com.sbz.databaseJPA.domain.entities.Book;
import com.sbz.databaseJPA.repositories.BookRepository;
import com.sbz.databaseJPA.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(String isbn, Book book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
