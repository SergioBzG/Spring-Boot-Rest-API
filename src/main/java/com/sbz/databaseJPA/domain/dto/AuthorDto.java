package com.sbz.databaseJPA.domain.dto;

import lombok.Builder;

// In this class can be used all lombok annotation to simplify code, but I didn't want it
@Builder
public class AuthorDto {
    private Long id;
    private String name;
    private Integer age;

    public AuthorDto(){

    }

    public AuthorDto(Long id, String name, Integer age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // All setters and getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
