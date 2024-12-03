package org.example.backend.services;

import org.example.backend.models.entities.Book;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.repositories.BookRepository;
import org.example.backend.services.mappers.BookListingDTOMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    private List<Book> books;
    private BookListingDTO bookListingDTO;
    private List<BookListingDTO> bookListingDTOS;
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        if (testInfo.getDisplayName().equals("testListBooks()")) {
            books = new ArrayList<>();
            bookListingDTOS = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                book = Book.builder()
                    .bookId(i)
                    .bookTitle("testBook"+i)
                    .isbn("123456789"+i)
                    .ratingsCount(1)
                    .rating(new BigDecimal("5.0"))
                    .summary("testSummary"+i)
                    .bookCover(new byte[0])
                    .languageOfOrigin("English")
                    .publicationDate(new Date())
                    .publisher("testPublisher"+i)
                    .genre("testGenre"+i)
                    .build();

                bookListingDTO = new BookListingDTOMapper().apply(book);

                books.add(book);
                bookListingDTOS.add(bookListingDTO);
            }
        }
        else {
            book = Book.builder()
                .bookId(1)
                .bookTitle("testBook")
                .isbn("1234567890")
                .ratingsCount(1)
                .rating(new BigDecimal("5.0"))
                .summary("testSummary")
                .bookCover(new byte[0])
                .languageOfOrigin("English")
                .publicationDate(new Date())
                .publisher("testPublisher")
                .genre("testGenre")
                .build();

            bookListingDTO = new BookListingDTOMapper().apply(book);
        }
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1);

        assertEquals(book, result);
    }

    @Test
    public void testListBooks() {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(0,pageSize);

        List<Book> paginatedBooks = books.subList(0, Math.min(books.size(), pageSize));
        Page<Book> bookPage = new PageImpl<>(paginatedBooks,pageable,books.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookListingDTOMapper.apply(any(Book.class))).thenReturn(bookListingDTO);

        List<BookListingDTO> result = bookService.listBooks(pageable);

        assertEquals(pageSize, result.size());
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