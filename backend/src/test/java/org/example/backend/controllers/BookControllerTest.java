package org.example.backend.controllers;

import org.example.backend.models.dtos.BookDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Test
    void testGetBookById() throws Exception {
        BookDTO mockBook = new BookDTO("title1", "isbn1", 0, new BigDecimal(0), "summary1", null, "English", null, "publisher1", "genre1", null);
        Mockito.when(bookService.getBookPageViewById(anyInt())).thenReturn(mockBook);

        mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookTitle", is("title1")));
    }

    @Test
    void testGetBooksCount() throws Exception {
        Mockito.when(bookService.getBooksCount()).thenReturn(10L);

        mockMvc.perform(get("/books/count")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    void testAddBook() throws Exception {
        BookDTO bookDTO = new BookDTO("title1", "isbn1", 0, new BigDecimal(0), "summary1", null, "English", null, "publisher1", "genre1", null);
        Mockito.when(bookService.addBook(any())).thenReturn(ResponseEntity.ok("Book added successfully"));

        mockMvc.perform(post("/books/add-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookTitle\":\"title1\",\"isbn\":\"isbn1\",\"summary\":\"summary1\",\"languageOfOrigin\":\"English\",\"publisher\":\"publisher1\",\"genre\":\"genre1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book added successfully"));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookDTO bookDTO = new BookDTO("title1", "isbn1", 0, new BigDecimal(0), "summary1", null, "English", null, "publisher1", "genre1", null);
        Mockito.when(bookService.updateBook(anyInt(), any())).thenReturn(ResponseEntity.ok("Book updated successfully"));

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookTitle\":\"title1\",\"isbn\":\"isbn1\",\"summary\":\"summary1\",\"languageOfOrigin\":\"English\",\"publisher\":\"publisher1\",\"genre\":\"genre1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book updated successfully"));
    }

    @Test
    void testDeleteBookByIsbn() throws Exception {
        Mockito.when(bookService.deleteBookByIsbn(anyString())).thenReturn(ResponseEntity.ok("Book deleted successfully"));

        mockMvc.perform(delete("/books/delete-by-isbn")
                        .param("isbn", "isbn1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));
    }
}
