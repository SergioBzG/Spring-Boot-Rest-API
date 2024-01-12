package com.sbz.databaseJPA.services.impl;

import com.sbz.databaseJPA.domain.entities.Author;
import com.sbz.databaseJPA.repositories.AuthorRepository;
import com.sbz.databaseJPA.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        Iterable<Author> authorIterable = authorRepository.findAll();
//        Iterator<Author> iterator = authorIterable.iterator();
//        List<Author> result = new ArrayList<>();

        // Different ways

        /*
        while(iterator.hasNext()){
            result.add(authorIterable.iterator().next());
        }
        return result;
         */

        /*
        authorIterable.forEach(result::add);
        return result;
         */
        return StreamSupport.stream(authorIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Author> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public Author partialUpdate(Long id, Author authorEntity) {
        authorEntity.setId(id);

        // Retrieve author from database and then updated it with authorEntity's data
        return authorRepository.findById(id).map(existingAuthor -> { // if we find the authorEntity as provided has an age and, it's not null then to set that on the existingAuthor  as we found it from the db (It's the same with the name)
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);

            // Save changes
            return authorRepository.save(existingAuthor);
        }).orElseThrow( // It's unlikely that this happens, because a non-existing author is handled in the AuthorController
                () -> new RuntimeException("Author does not exist")
        );
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
