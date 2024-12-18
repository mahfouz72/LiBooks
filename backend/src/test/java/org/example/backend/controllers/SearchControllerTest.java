package org.example.backend.controllers;

import org.example.backend.models.dtos.AuthorDTO;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.dtos.UserDTO;
import org.example.backend.services.filters.FilterFactory;
import org.example.backend.services.filters.SearchFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SearchControllerTest {

    private MockMvc mockMvc;
    private FilterFactory filterFactory;
    private SearchController searchController;

    @BeforeEach
    public void setup() {
        // Create mocks
        filterFactory = Mockito.mock(FilterFactory.class);
        searchController = new SearchController(filterFactory);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void testSearchBooks_Success() throws Exception {
        // Prepare test data
        List<BookListingDTO> bookResults = Arrays.asList(
                new BookListingDTO(1, "Test Book 1", BigDecimal.valueOf(4.5), null, null),
                new BookListingDTO(2, "Test Book 2", BigDecimal.valueOf(4.2), null, null)
        );

        // Create a mock SearchFilter specifically for books
        SearchFilter bookFilter = Mockito.mock(SearchFilter.class);

        // Use doReturn to explicitly set the return value
        Mockito.doReturn(bookFilter).when(filterFactory).getFilter("books");
        Mockito.doReturn(bookResults).when(bookFilter).applyFilter(anyString());

        // Perform the test
        mockMvc.perform(get("/search/books")
                        .param("query", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].bookId").value(1))
                .andExpect(jsonPath("$[0].bookTitle").value("Test Book 1"));
    }

    @Test
    public void testSearchAuthors_Success() throws Exception {
        // Prepare test data
        List<AuthorDTO> authorResults = Arrays.asList(
                new AuthorDTO(1, "John Doe", new byte[]{1, 2, 3}),
                new AuthorDTO(2, "Jane Smith", new byte[]{4, 5, 6})
        );

        // Create a mock SearchFilter specifically for authors
        SearchFilter authorFilter = Mockito.mock(SearchFilter.class);

        // Use doReturn to explicitly set the return value
        Mockito.doReturn(authorFilter).when(filterFactory).getFilter("authors");
        Mockito.doReturn(authorResults).when(authorFilter).applyFilter(anyString());

        // Perform the test
        mockMvc.perform(get("/search/authors")
                        .param("query", "john")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].authorName").value("John Doe"));
    }

    @Test
    public void testSearchUsers_Success() throws Exception {
        // Prepare test data
        List<UserDTO> userResults = Arrays.asList(
                new UserDTO(1, "johndoe", "john@example.com",
                        LocalDate.of(1990, 1, 1),
                        LocalDate.of(2023, 1, 1)),
                new UserDTO(2, "janesmith", "jane@example.com",
                        LocalDate.of(1995, 5, 15),
                        LocalDate.of(2023, 2, 1))
        );

        // Create a mock SearchFilter specifically for users
        SearchFilter userFilter = Mockito.mock(SearchFilter.class);

        // Use doReturn to explicitly set the return value
        Mockito.doReturn(userFilter).when(filterFactory).getFilter("users");
        Mockito.doReturn(userResults).when(userFilter).applyFilter(anyString());

        // Perform the test
        mockMvc.perform(get("/search/users")
                        .param("query", "john")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("johndoe"));
    }

    @Test
    public void testSearch_UnknownCategory_ThrowsException() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("Unknown category: unknown"))
                .when(filterFactory).getFilter("unknown");

        // Perform the test
        mockMvc.perform(get("/search/unknown")
                        .param("query", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}