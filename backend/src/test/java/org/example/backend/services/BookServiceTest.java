package org.example.backend.services;

import org.example.backend.models.entities.Book;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.repositories.BookRepository;
import org.example.backend.services.mappers.BookListingDTOMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookListingDTOMapper bookListingDTOMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookListingDTO bookListingDTO;

    @BeforeEach
    public void setUp() {
        book = Book.builder()
                .bookId(1)
                .bookTitle("testBook")
                .isbn("1234567890")
                .ratingsCount(1)
                .rating(new BigDecimal(5.0))
                .summary("testSummary")
                .bookCover(new byte[0])
                .languageOfOrigin("English")
                .publicationDate(new Date())
                .publisher("testPublisher")
                .genre("testGenre")
                .build();

        bookListingDTO = new BookListingDTOMapper().apply(book);
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1);

        assertEquals(book, result);
    }

    @Test
    public void testListBooks() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookListingDTOMapper.apply(any(Book.class))).thenReturn(bookListingDTO);

        List<BookListingDTO> result = bookService.listBooks();

        assertEquals(books.size(), result.size());
        assertEquals(bookListingDTO, result.get(0));
    }

    @Test
    public void testSaveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.saveBook(book);

        assertEquals(book, result);
    }

    @Test
    public void testDeleteBook() {
        bookService.deleteBook(1);

        assertEquals(0, bookRepository.findAll().size());
    }
}