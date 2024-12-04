package org.example.backend.controllers;

import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.security.JWTService;
import org.example.backend.services.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private BookService bookService;

    @Test
    void testGetAllBooks() throws Exception {
        // Mock data
        List<BookListingDTO> mockBooks = Arrays.asList(
            new BookListingDTO(1, "title1", new BigDecimal(3), null, null),
            new BookListingDTO(1, "title2", new BigDecimal(4), null, null)
        );
        when(bookService.listBooks(Mockito.any())).thenReturn(mockBooks);

        mockMvc.perform(get("/books")
                .param("page", "0")
                .param("size", "2")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].bookTitle", is("title1")))
            .andExpect(jsonPath("$[1].bookTitle", is("title2")));
    }
}
