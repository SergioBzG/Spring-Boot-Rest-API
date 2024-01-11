package com.sbz.databaseJPA.services;

import com.sbz.databaseJPA.domain.entities.Book;

import java.util.List;

public interface BookService {

    Book createBook(String isbn, Book book);

    List<Book> findAll();
}
