package com.sbz.databaseJPA.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbz.databaseJPA.TestDataUtil;
import com.sbz.databaseJPA.domain.entities.Author;
import com.sbz.databaseJPA.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private final AuthorService authorService;
    private final MockMvc mockMvc;

    // This attribute is used to take Java objects and turn them into Json, because requests receive Json objects in their bodies
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(final MockMvc mockMvc, final AuthorService authorService) {
        this.authorService = authorService;
        this.mockMvc = mockMvc;
        // Does not exist an ObjectMapper in the context (no bean)
        this.objectMapper = new ObjectMapper();
    }

    // Check that the [POST] /authors endpoint response with 201 status code
    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        testAuthor.setId(null);

        // To turn testAuthor into Json
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                // Here the request is created
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect( // Here is an assertion created (they can be chained)
                MockMvcResultMatchers.status().isCreated()
        );
    }

    // Check that the [POST] /authors endpoint response with the author created
    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthorA();
        testAuthor.setId(null);

        // To turn testAuthor into Json
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                // Here the request is created
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect( // Here is an assertion created (they can be chained)
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    // Check that the [GET] /authors endpoint response with 200 status code
    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    // Check that the [GET] /authors endpoint response with a list of authors
    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        // Create author in db
        Author author = TestDataUtil.createTestAuthorA();
        authorService.createAuthor(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        );
    }

    // Check that the [GET] /authors/{id} endpoint response with 200 status code
    @Test
    public void testThatGetAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        // Create author in db
        Author author = TestDataUtil.createTestAuthorA();
        authorService.createAuthor(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    // Check that the [GET] /authors/{id} endpoint response with 404 status code
    @Test
    public void testThatGetAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    // Check that the [GET] /authors/{id} endpoint response with an author
    @Test
    public void testThatGetAuthorReturnsAuthorWhenAuthorExists() throws Exception {
        // Create author in db
        Author author = TestDataUtil.createTestAuthorA();
        authorService.createAuthor(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

}
