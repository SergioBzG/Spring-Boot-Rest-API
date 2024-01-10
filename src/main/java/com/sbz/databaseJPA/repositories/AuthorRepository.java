package com.sbz.databaseJPA.repositories;

import com.sbz.databaseJPA.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    // Custom Queries
    // Get authors with age less than
    Iterable<Author> ageLessThan(int age);

    // Using HQL
    // Get authors with age greater than 50
    @Query("SELECT a FROM Author a WHERE a.age > ?1")
    Iterable<Author> findAuthorsWithAgeGreaterThan(int age);
}
