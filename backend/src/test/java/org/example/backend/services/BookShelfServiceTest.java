package org.example.backend.services;

import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.dtos.BookShelfDTO;
import org.example.backend.models.dtos.BooksBookShelfDTO;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.BookShelf;
import org.example.backend.models.entities.BooksBookShelf;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.BookShelfRepository;
import org.example.backend.repositories.BooksBookShelfRepository;
import org.example.backend.services.mappers.BookShelfDTOMapper;
import org.example.backend.services.mappers.BooksBookShelfDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookShelfServiceTest {

    @Mock
    private BookShelfRepository bookShelfRepository;

    @Mock
    private BooksBookShelfRepository booksBookShelfRepository;

    @Mock
    private UserAuthenticationService userAuthenticationService;

    @Mock
    private UserService userService;

    @Mock
    private BookShelfDTOMapper bookShelfDTOMapper;

    @Mock
    private BooksBookShelfDTOMapper booksBookShelfDTOMapper;

    @InjectMocks
    private BookShelfService bookShelfService;

    private List<User> users;

    private List<BookShelf> bookShelves;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        users = new ArrayList<>();
        bookShelves = new ArrayList<>();
        //bookShelvesDtos = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            User user = new User();
            user.setId(i);
            
            users.add(user);
        }

        for (int i = 1; i <= 12; i++) {
            BookShelf bookShelf =
                BookShelf.builder()
                    .bookShelfId(i)
                    .bookShelfName("bookShelf"+i)
                    .user(i<6 ? users.get(0) : users.get(1))
                    .build();

            bookShelves.add(bookShelf);
        }

    }

    @Test
    void testGetBookShelfNames() {
        int pageSize = 5;
        User user1 = users.get(0);

        Pageable pageable = PageRequest.of(0, pageSize);
        List<BookShelf> paginatedBookShelves =
            bookShelves.subList(0, Math.min(bookShelves.size(), pageSize));

        Page<BookShelf> bookShelfPage = new PageImpl<>(paginatedBookShelves);

        when(userAuthenticationService.getCurrentUsername()).thenReturn("user1");
        when(userService.getUserByUsername("user1")).thenReturn(user1);
        when(bookShelfRepository.findByUserId(user1.getId(), pageable))
            .thenReturn(bookShelfPage);
        when(bookShelfDTOMapper.apply(any(BookShelf.class))).
            thenAnswer(invocationOnMock -> {
               BookShelf bs = invocationOnMock.getArgument(0);
               return new BookShelfDTO(bs.getBookShelfId(), bs.getBookShelfName());
            });

        List<BookShelfDTO> result = bookShelfService.getBookShelfNames(pageable);

        assertEquals(5, result.size());
        for (int i = 1; i <= 5; i++) {
            assertEquals("bookShelf"+ i, result.get(i-1).bookShelfName());
        }
    }

    @Test
    void testAddBookShelf() {
        BookShelf bookShelf = new BookShelf(2, users.get(0), "book2");
        BookShelfDTO bookShelfDTO = new BookShelfDTO(2, "book2");

        when(userAuthenticationService.getCurrentUsername()).thenReturn("user1");
        when(userService.getUserByUsername("user1")).thenReturn(users.get(0));
        when(bookShelfRepository.save(any(BookShelf.class))).thenReturn(bookShelf);
        when(bookShelfDTOMapper.apply(bookShelf)).
            thenReturn(new BookShelfDTO(2, "book2"));

        BookShelfDTO result = bookShelfService.addBookShelf(bookShelfDTO);

        assertNotNull(result);
        assertEquals("book2", result.bookShelfName());
        assertEquals(2, result.bookShelfId());
    }

    @Test
    void testRenameBookShelf() {
        int bookShelfId = 1;
        BookShelfDTO bookShelfDTO =
            new BookShelfDTO(1, "New Name");
        BookShelf bookShelf =
            new BookShelf(1, users.get(0), "Old Name");

        when(bookShelfRepository.findById(bookShelfId)).thenReturn(Optional.of(bookShelf));
        when(userAuthenticationService.getCurrentUsername()).thenReturn("user1");
        when(userService.getUserByUsername("user1")).thenReturn(users.get(0));
        when(bookShelfRepository.save(bookShelf)).thenReturn(bookShelf);
        when(bookShelfDTOMapper.apply(bookShelf)).thenReturn(bookShelfDTO);

        ResponseEntity<?> response = bookShelfService.
            renameBookShelf(bookShelfId, bookShelfDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookShelfDTO, response.getBody());
        verify(bookShelfRepository, times(1)).save(bookShelf);
    }

    @Test
    void testDeleteBookShelf() {
        int bookShelfId = 1;

        bookShelfService.deleteBookShelf(bookShelfId);

        verify(bookShelfRepository, times(1)).deleteById(bookShelfId);
    }

    @Test
    void testGetBooksInBookShelf() {
        int bookShelfId = 1;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(0, pageSize);
        Book book = Book.builder()
            .bookId(1)
            .bookTitle("book1").build();

        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));
        when(booksBookShelfRepository.findBooksByBookShelfId(bookShelfId, pageable)).
            thenReturn(bookPage);

        List<BookListingDTO> result = bookShelfService.getBooksInBookShelf(bookShelfId, pageable);

        assertEquals(1, result.size());
        verify(booksBookShelfRepository,
            times(1)).findBooksByBookShelfId(bookShelfId, pageable);
    }

    @Test
    void testAddBookInBookShelf() {
        BooksBookShelfDTO dto = new BooksBookShelfDTO(1, 2);
        BooksBookShelf booksBookShelf = new BooksBookShelf();
        Book book = Book.builder()
            .bookId(2)
            .bookTitle("book2").build();
        booksBookShelf.setBook(book);

        when(booksBookShelfDTOMapper.apply(dto)).thenReturn(booksBookShelf);
        when(booksBookShelfRepository.save(booksBookShelf)).thenReturn(booksBookShelf);

        BookListingDTO result = bookShelfService.addBookInBookShelf(dto);

        assertNotNull(result);
        verify(booksBookShelfRepository, times(1)).save(booksBookShelf);
    }

    @Test
    void testDeleteBookInBookShelf() {
        BooksBookShelfDTO dto = new BooksBookShelfDTO(1, 2);
        BooksBookShelf booksBookShelf = new BooksBookShelf();

        when(booksBookShelfDTOMapper.apply(dto)).thenReturn(booksBookShelf);

        bookShelfService.deleteBookInBookShelf(dto);

        verify(booksBookShelfRepository, times(1)).delete(booksBookShelf);
    }
}
