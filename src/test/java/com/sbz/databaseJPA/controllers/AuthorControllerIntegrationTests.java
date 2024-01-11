package com.sbz.databaseJPA.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbz.databaseJPA.TestDataUtil;
import com.sbz.databaseJPA.domain.entities.Author;
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

    private final MockMvc mockMvc;

    // This attribute is used to take Java objects and turn them into Json, because requests receive Json objects in their bodies
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(final MockMvc mockMvc) {
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

}
