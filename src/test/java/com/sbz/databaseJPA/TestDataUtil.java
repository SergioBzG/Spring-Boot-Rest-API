package com.sbz.databaseJPA;


import com.sbz.databaseJPA.domain.dto.AuthorDto;
import com.sbz.databaseJPA.domain.dto.BookDto;
import com.sbz.databaseJPA.domain.entities.Author;
import com.sbz.databaseJPA.domain.entities.Book;

// A utility class usually follows a pattern of being final
public final class TestDataUtil {

    private TestDataUtil() {}

    public static Author createTestAuthorA() {
        //TODO: read about @Builder annotation and Builder Pattern
        // Builder pattern ?
        return Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Calypso")
                .age(23)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Tylor")
                .age(28)
                .build();
    }

    public static AuthorDto createTestAuthorDto() {
        return AuthorDto.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .isbn("087-1-035-324")
                .title("Cassandra")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("345-56-23-2")
                .title("The shadow of the killer")
                .author(author)
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("078-65-5-681")
                .title("Castle of Glasses")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return BookDto.builder()
                .isbn("078-65-5-681")
                .title("Castle of Glasses")
                .author(author)
                .build();
    }
}
