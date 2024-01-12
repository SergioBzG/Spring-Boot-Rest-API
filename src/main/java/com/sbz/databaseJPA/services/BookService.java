package com.sbz.databaseJPA.services;

import com.sbz.databaseJPA.domain.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book saveBook(String isbn, Book book);

    List<Book> findAll();


    Optional<Book> findOne(String isbn);

    boolean isExists(String isbn);

    Book partialUpdate(String isbn, Book bookEntity);

    void delete(String isbn);
}
