package com.sbz.databaseJPA.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbz.databaseJPA.TestDataUtil;
import com.sbz.databaseJPA.domain.dto.BookDto;
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
public class BookControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    // Check that the [PUT] /books/{isbn} endpoint response with 201 status code
    @Test
    public void testThatCreateBookReturnsHttpStatusCode201Created() throws Exception {
        BookDto testBook = TestDataUtil.createTestBookDtoA(null);

        // To turn testBook into Json
        String bookJson = objectMapper.writeValueAsString(testBook);

        mockMvc.perform(
                // Here the request is created
                MockMvcRequestBuilders.put("/books/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                // Here is an assertion created (they can be chained)
                MockMvcResultMatchers.status().isCreated()
        );

    }

    // Check that the [PUT] /books/{isbn} endpoint response with the book created
    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception {
        BookDto testBook = TestDataUtil.createTestBookDtoA(null);

        // To turn testBook into Json
        String bookJson = objectMapper.writeValueAsString(testBook);

        mockMvc.perform(
                // Here the request is created
                MockMvcRequestBuilders.put("/books/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                // Here is an assertion created (they can be chained)
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBook.getTitle())
        );
    }
}
