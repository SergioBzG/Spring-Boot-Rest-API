package com.sbz.databaseJPA.mappers.impl;

import com.sbz.databaseJPA.domain.dto.AuthorDto;
import com.sbz.databaseJPA.domain.entities.Author;
import com.sbz.databaseJPA.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<Author, AuthorDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public AuthorMapperImpl(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public Author mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }
}
