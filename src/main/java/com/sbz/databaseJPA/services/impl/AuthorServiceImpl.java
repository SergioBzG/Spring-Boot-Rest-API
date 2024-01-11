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
}
