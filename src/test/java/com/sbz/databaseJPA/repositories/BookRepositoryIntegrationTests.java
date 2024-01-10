package com.sbz.databaseJPA.repositories;


import com.sbz.databaseJPA.TestDataUtil;
import com.sbz.databaseJPA.domain.Author;
import com.sbz.databaseJPA.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {


    private final BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(final BookRepository underTest) {
        this.underTest = underTest;
    }

    // Tests

    // Create and Read a Book
    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        // Create author who will be assigned to the book
        Author author = TestDataUtil.createTestAuthorA();

        // The author related with book can be passed as nested object thanks to the value of cascade attribute defined in book entity
        Book book = TestDataUtil.createTestBookA(author);
        // Create book
        underTest.save(book);
        // Read book
        Optional<Book> result = underTest.findById(book.getIsbn());

        // Check that's not an Optional empty, but that it contains a book inside.
        assertThat(result).isPresent();
        // Check that the book created is equal to who was sent it.
        assertThat(result.get()).isEqualTo(book);

    }

    // Create and Read multiple books
    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecall() {
        // Create the author who will be assigned to the all books
        Author author = TestDataUtil.createTestAuthorA();

        Book bookA = TestDataUtil.createTestBookA(author);
        Book bookB = TestDataUtil.createTestBookB(author);
        Book bookC = TestDataUtil.createTestBookC(author);

        // Create books
        underTest.save(bookA);
        underTest.save(bookB);
        underTest.save(bookC);

        // Read books
        Iterable<Book> result = underTest.findAll();

        // Asserts chain
        // Check that the number of books created is three and that they are exactly the ones sent.
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }

    // Update a book by id
    @Test
    public void testThatBookCanBeUpdated() {
        // Create the author who will be assigned to the book
        Author author = TestDataUtil.createTestAuthorA();

        // Create book
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);

        // Update book
        book.setTitle("UPDATED");
        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        // Create the author who will be assigned to the book
        Author author = TestDataUtil.createTestAuthorA();

        // Create book
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);

        // Delete book
        underTest.deleteById(book.getIsbn());

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isEmpty();
    }
}
