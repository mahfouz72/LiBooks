package org.example.backend.services;

import org.example.backend.models.dtos.AuthorDTO;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.entities.Author;
import org.example.backend.models.entities.Book;
import org.example.backend.repositories.AuthorRepository;
import org.example.backend.services.mappers.AuthorDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    private AuthorService authorService;
    private AuthorRepository authorRepository;
    private AuthorDTOMapper authorDTOMapper;

    @BeforeEach
    public void setup() {
        authorRepository = Mockito.mock(AuthorRepository.class);
        authorDTOMapper = Mockito.mock(AuthorDTOMapper.class);
        authorService = new AuthorService(authorRepository, authorDTOMapper);
    }

    @Test
    void testAddAuthor() {
        AuthorDTO authorDTO = new AuthorDTO(1, "Author Name", null, "Biography", null, "Nationality");
        Author author = Author.builder()
                .authorId(1)
                .authorName("Author Name")
                .biography("Biography")
                .nationality("Nationality")
                .build();

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        ResponseEntity<String> response = authorService.addAuthor(authorDTO);
        assertEquals("Author added successfully", response.getBody());
    }

    @Test
    void testGetAllAuthors() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Author> authors = Arrays.asList(Author.builder()
                .authorId(1)
                .authorName("Author Name")
                .biography("Biography")
                .nationality("Nationality")
                .build());
        Page<Author> page = new PageImpl<>(authors);

        when(authorRepository.findAll(pageable)).thenReturn(page);
        when(authorDTOMapper.apply(any(Author.class))).thenReturn(new AuthorDTO(1, "Author Name", null, "Biography", null, "Nationality"));

        List<AuthorDTO> authorDTOs = authorService.getAllAuthors(pageable);
        assertEquals(1, authorDTOs.size());
    }

    @Test
    void testGetAuthorsNames() {
        Pageable pageable = PageRequest.of(0, 5);
        List<AuthorDTO> authorDTOs = Arrays.asList(new AuthorDTO(1, "Author Name", null));

        when(authorRepository.getAuthorsNames(pageable)).thenReturn(authorDTOs);

        List<AuthorDTO> result = authorService.getAuthorsNames(pageable);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAuthorById() {
        Author author = Author.builder()
                .authorId(1)
                .authorName("Author Name")
                .biography("Biography")
                .nationality("Nationality")
                .build();

        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(author));
        when(authorDTOMapper.apply(any(Author.class))).thenReturn(new AuthorDTO(1, "Author Name", null, "Biography", null, "Nationality"));

        AuthorDTO authorDTO = authorService.getAuthorById(1);
        assertEquals("Author Name", authorDTO.authorName());
    }

    @Test
    void testGetAuthorBooksIds() {
        List<Integer> bookIds = Arrays.asList(1, 2, 3);

        when(authorRepository.getAuthorBooksIds(anyInt())).thenReturn(bookIds);

        List<Integer> result = authorService.getAuthorBooksIds(1);
        assertEquals(3, result.size());
    }

    @Test
    void testUpdateAuthor() {
        AuthorDTO authorDTO = new AuthorDTO(1, "Updated Name", null, "Updated Biography", null, "Updated Nationality");
        Author author = Author.builder()
                .authorId(1)
                .authorName("Author Name")
                .biography("Biography")
                .nationality("Nationality")
                .build();

        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        ResponseEntity<String> response = authorService.updateAuthor(authorDTO);
        assertEquals("Author updated successfully", response.getBody());
    }

    @Test
    void testDeleteAuthor() {
        Author author = Author.builder()
                .authorId(1)
                .authorName("Author Name")
                .biography("Biography")
                .nationality("Nationality")
                .build();

        when(authorRepository.findById(anyInt())).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).delete(any(Author.class));

        ResponseEntity<String> response = authorService.deleteAuthor(1);
        assertEquals("Author deleted successfully", response.getBody());
    }

    @Test
    void testGetAuthorBooks() {
        Pageable pageable = PageRequest.of(0, 5);
        List<BookListingDTO> bookListingDTOs = Arrays.asList(new BookListingDTO(1, "Book Title", null, null, null));
        Page<Book> books = new PageImpl<>(Arrays.asList(new Book()));

        when(authorRepository.getAuthorBooks(anyInt(), any(Pageable.class))).thenReturn(books);
        when(authorDTOMapper.apply(any(Author.class))).thenReturn(new AuthorDTO(1, "Author Name", null, "Biography", null, "Nationality"));

        List<BookListingDTO> result = authorService.getAuthorBooks(1, pageable);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAuthorsCount() {
        when(authorRepository.count()).thenReturn(10L);

        Long count = authorService.getAuthorsCount();
        assertEquals(10, count);
    }
}