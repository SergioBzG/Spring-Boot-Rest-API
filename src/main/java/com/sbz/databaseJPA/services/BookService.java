package com.sbz.databaseJPA.services;

import com.sbz.databaseJPA.domain.entities.Book;

public interface BookService {

    Book createBook(String isbn, Book book);
}
