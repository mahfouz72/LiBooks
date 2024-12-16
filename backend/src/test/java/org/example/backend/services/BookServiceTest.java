package org.example.backend.services;

import org.example.backend.models.dtos.BookDTO;
import org.example.backend.models.entities.*;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.repositories.BookRepository;
import org.example.backend.services.mappers.BookDTOMapper;
import org.example.backend.services.mappers.BookListingDTOMapper;
import org.example.backend.models.entities.compositeKeys.AuthorBookID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

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

    @Mock
    private BookDTOMapper bookDTOMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private List<Book> books;

    private Author author;
    private List<Author> authors;

    private List<AuthorBook> authorBooks;

    private BookListingDTO bookListingDTO;
    private List<BookListingDTO> bookListingDTOS;
    
    private AuthorBook addEntryAuthorBook(int authorId, int bookId) {
        AuthorBook authorBook = AuthorBook.builder()
            .id(new AuthorBookID(authorId, bookId))
            .author(authors.get(authorId))
            .book(books.get(bookId))
            .build();

        return authorBook;
    }

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        
        books = new ArrayList<>();
        authors = new ArrayList<>();
        authorBooks = new ArrayList<>();
        bookListingDTOS = new ArrayList<>();

        for (int i=0; i<3; i++) {
            author = Author.builder()
                .authorId(i)
                .authorName("testAuthor"+i)
                .build();

            authors.add(author);
        }

        for (int i = 0; i < 10; i++) {
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

                books.add(book);
        }

        for (int i = 0; i < 10; i++) {
            authorBooks.add(addEntryAuthorBook(1,i));
        }

        authorBooks.add(addEntryAuthorBook(2,1));
        authorBooks.add(addEntryAuthorBook(2,2));
        authorBooks.add(addEntryAuthorBook(2,3));

        for (int i = 0; i < 10; i++) {
            bookListingDTO = bookListingDTOMapper.apply(books.get(i));
            bookListingDTOS.add(bookListingDTO);
        }

    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(0)).thenReturn(Optional.of(books.get(0)));

        Book result = bookService.getBookById(0);

        assertEquals(books.get(0), result);
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

        for (int i = 0; i < result.size(); i++) {
            System.out.println(bookListingDTOS.get(i));
        }

        assertEquals(pageSize, result.size());
        assertEquals(bookListingDTO, result.get(0));
    }

    @Test
    public void testSaveBook() {
        when(bookRepository.save(books.get(0))).thenReturn(books.get(0));

        Book result = bookService.saveBook(books.get(0));

        assertEquals(books.get(0), result);
    }

    @Test
    public void testDeleteBook() {
        bookService.deleteBook(0);
    }

    @Test
    public void getBookPageViewById() {
        Book book = books.get(0);
        when(bookRepository.findById(0)).thenReturn(Optional.of(book));
        when(bookDTOMapper.apply(books.get(0))).thenReturn(
                new BookDTO(
            book.getBookTitle(),
            book.getIsbn(),
            book.getRatingsCount(),
            book.getRating(),
            book.getSummary(),
            book.getBookCover(),
            book.getLanguageOfOrigin(),
            book.getPublicationDate(),
            book.getPublisher(),
            book.getGenre(),
            book.getAuthors()
        ));

        BookDTO result = bookService.getBookPageViewById(0);

        assertEquals(bookDTOMapper.apply(books.get(0)), result);
    }
}
