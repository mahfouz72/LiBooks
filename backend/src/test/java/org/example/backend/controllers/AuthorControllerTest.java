package org.example.backend.controllers;

import org.example.backend.models.dtos.AuthorDTO;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthorControllerTest {

    private MockMvc mockMvc;
    private AuthorService authorService;
    private AuthorController authorController;

    @BeforeEach
    public void setup() {
        authorService = Mockito.mock(AuthorService.class);
        authorController = new AuthorController(authorService);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    public void testAddAuthor() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO(1, "John Doe");
        Mockito.when(authorService.addAuthor(any(AuthorDTO.class)))
                .thenReturn(ResponseEntity.ok("Author added successfully"));

        mockMvc.perform(post("/api/authors/add-author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorId\":1,\"authorName\":\"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Author added successfully"));
    }

    @Test
    public void testGetAuthorsCount() throws Exception {
        Mockito.when(authorService.getAuthorsCount()).thenReturn(10L);

        mockMvc.perform(get("/api/authors/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        List<AuthorDTO> authors = Arrays.asList(
                new AuthorDTO(1, "John Doe", null),
                new AuthorDTO(2, "Jane Smith", null)
        );
        Mockito.when(authorService.getAllAuthors(any())).thenReturn(authors);

        mockMvc.perform(post("/api/authors/all")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].authorId").value(1))
                .andExpect(jsonPath("$[0].authorName").value("John Doe"))
                .andExpect(jsonPath("$[1].authorId").value(2))
                .andExpect(jsonPath("$[1].authorName").value("Jane Smith"));
    }

    @Test
    public void testGetAuthorsNames() throws Exception {
        List<AuthorDTO> authors = Arrays.asList(
                new AuthorDTO(1, "John Doe", null),
                new AuthorDTO(2, "Jane Smith", null)
        );
        Mockito.when(authorService.getAuthorsNames(any())).thenReturn(authors);

        mockMvc.perform(post("/api/authors/names")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].authorId").value(1))
                .andExpect(jsonPath("$[0].authorName").value("John Doe"))
                .andExpect(jsonPath("$[1].authorId").value(2))
                .andExpect(jsonPath("$[1].authorName").value("Jane Smith"));
    }

    @Test
    public void testGetAuthorById() throws Exception {
        AuthorDTO author = new AuthorDTO(1, "John Doe", null);
        Mockito.when(authorService.getAuthorById(anyInt())).thenReturn(author);

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId").value(1))
                .andExpect(jsonPath("$.authorName").value("John Doe"));
    }

    @Test
    public void testGetAuthorBooksIds() throws Exception {
        List<Integer> bookIds = Arrays.asList(1, 2, 3);
        Mockito.when(authorService.getAuthorBooksIds(anyInt())).thenReturn(bookIds);

        mockMvc.perform(get("/api/authors/1/books-ids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value(1))
                .andExpect(jsonPath("$[1]").value(2))
                .andExpect(jsonPath("$[2]").value(3));
    }

    @Test
    public void testGetAuthorBooks() throws Exception {
        List<BookListingDTO> books = Arrays.asList(
                new BookListingDTO(1, "Book 1", null, null, null),
                new BookListingDTO(2, "Book 2", null, null, null)
        );
        Mockito.when(authorService.getAuthorBooks(anyInt(), any())).thenReturn(books);

        mockMvc.perform(post("/api/authors/books")
                        .param("id", "1")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].bookTitle").value("Book 1"))
                .andExpect(jsonPath("$[1].bookTitle").value("Book 2"));
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO(1, "John Doe");
        Mockito.when(authorService.updateAuthor(any(AuthorDTO.class)))
                .thenReturn(ResponseEntity.ok("Author updated successfully"));

        mockMvc.perform(put("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorId\":1,\"authorName\":\"John Doe\",\"nationality\":\"USA\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Author updated successfully"));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Mockito.when(authorService.deleteAuthor(anyInt()))
                .thenReturn(ResponseEntity.ok("Author deleted successfully"));

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Author deleted successfully"));
    }
}