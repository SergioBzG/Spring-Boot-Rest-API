package com.sbz.databaseJPA.repositories;


import com.sbz.databaseJPA.TestDataUtil;
import com.sbz.databaseJPA.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// Here are the integration tests for AuthorDaoImpl
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    // Class being tested
    private final AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(final AuthorRepository underTest) {
        this.underTest = underTest;
    }

    // Tests

    // Create and Read an Author
    @Test
    public void testThatAuthorCanBeCreatedAndRecalled () {
        Author author = TestDataUtil.createTestAuthorA();
        // Create author
        underTest.save(author);
        // Read author
        Optional<Author> result = underTest.findById(author.getId());

        // Check that's not an Optional empty, but that it contains an author inside.
        assertThat(result).isPresent();
        // Check that the author created is equal to who was sent it.
        assertThat(result.get()).isEqualTo(author);
    }

    // Create and Read multiple Authors
    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecall() {
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        // Create authors
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        // Read authors
        Iterable<Author> result = underTest.findAll();

        // Asserts chain
        // Check that the number of authors created is three and that they are exactly the ones sent.
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);
    }

    // Update author by id
    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorA();
        // Create author
        underTest.save(author);

        // Update author
        // In JPA .save() is used to create and update
        author.setName("UPDATED");
        underTest.save(author);

        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    // Delete an author by id
    @Test
    public void testThatAuthorCanBeDeleted() {
        // Create author
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);

        // Delete author
        underTest.deleteById(author.getId());

        Optional<Author> result = underTest.findById(author.getId());
        // If the Optional is empty then the author was deleted
        assertThat(result).isEmpty();
    }

    // Get authors with age less than
    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        // Create authors
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        // Get authors with age less than 50
        Iterable<Author> result = underTest.ageLessThan(50);
        assertThat(result).containsExactly(authorB, authorC);
    }

    // Get authors with age less than
    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        // Create authors
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        // Get authors with age greater than 50
        Iterable<Author> result = underTest.findAuthorsWithAgeGreaterThan(50);
        assertThat(result).containsExactly(authorA);
    }
}
