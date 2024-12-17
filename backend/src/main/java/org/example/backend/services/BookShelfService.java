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
import org.example.backend.services.mappers.BookListingDTOMapper;
import org.example.backend.services.mappers.BookShelfDTOMapper;
import org.example.backend.services.mappers.BooksBookShelfDTOMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookShelfService {

    private BookShelfRepository bookShelfRepository;

    private BooksBookShelfRepository booksBookShelfRepository;

    private UserAuthenticationService userAuthenticationService;

    private UserService userService;

    private BookShelfDTOMapper bookShelfDTOMapper;

    private BooksBookShelfDTOMapper booksBookShelfDTOMapper;

    public BookShelfService(BookShelfRepository bookShelfRepository,
                            BooksBookShelfRepository booksBookShelfRepository,
                            UserAuthenticationService userAuthenticationService,
                            UserService userService, BookShelfDTOMapper bookShelfDTOMapper,
                            BooksBookShelfDTOMapper booksBookShelfDTOMapper) {
        this.bookShelfRepository = bookShelfRepository;
        this.booksBookShelfRepository = booksBookShelfRepository;
        this.userAuthenticationService = userAuthenticationService;
        this.userService = userService;
        this.bookShelfDTOMapper = bookShelfDTOMapper;
        this.booksBookShelfDTOMapper = booksBookShelfDTOMapper;
    }

    public List<BookShelfDTO> getBookShelfNames(Pageable pageable) {
        User currentUser = getCurrentUser();
        return bookShelfRepository.findByUserId(currentUser.getId(), pageable)
            .stream().map(bookShelfDTOMapper).collect(Collectors.toList());
    }

    public BookShelfDTO addBookShelf(BookShelfDTO bookShelfDTO) {
        String bookShelfName = bookShelfDTO.bookShelfName();
        User currentUser = getCurrentUser();

        BookShelf bookShelf = new BookShelf();
        bookShelf.setBookShelfName(bookShelfName);
        bookShelf.setUser(currentUser);
        return bookShelfDTOMapper.apply(bookShelfRepository.save(bookShelf));
    }

    public ResponseEntity<?> renameBookShelf(int bookShelfId, BookShelfDTO bookShelfDTO) {
        BookShelf bookShelf = bookShelfRepository.findById(bookShelfId).orElseThrow();
        User currentUser = getCurrentUser();
        ResponseEntity<?> response;
        if (!currentUser.equals(bookShelf.getUser())) {
            response = new ResponseEntity<>("Not User's Bookshelf ", HttpStatus.UNAUTHORIZED);
        }
        else {
            String newBookShelfName = bookShelfDTO.bookShelfName();
            bookShelf.setBookShelfName(newBookShelfName);
            bookShelfRepository.save(bookShelf);
            response = ResponseEntity.ok(bookShelfDTOMapper.apply(bookShelf));
        }
        return response;
    }

    public void deleteBookShelf(Integer bookShelfId) {
        // TODO check current user
        bookShelfRepository.deleteById(bookShelfId);
    }

    public List<BookListingDTO> getBooksInBookShelf(Integer bookShelfId, Pageable pageable) {
        // TODO check current user
        BookListingDTOMapper bookListingDTOMapper = new BookListingDTOMapper();
        Page<Book> books = booksBookShelfRepository.findBooksByBookShelfId(bookShelfId, pageable);
        return books.stream()
            .map(bookListingDTOMapper).collect(Collectors.toList());
    }

    public BookListingDTO addBookInBookShelf(BooksBookShelfDTO booksBookShelfDTO) {
        BooksBookShelf booksBookShelf =
            booksBookShelfRepository.save(booksBookShelfDTOMapper.apply(booksBookShelfDTO));

        BookListingDTOMapper bookListingDTOMapper = new BookListingDTOMapper();
        return bookListingDTOMapper.apply(booksBookShelf.getBook());
    }

    public void deleteBookInBookShelf(BooksBookShelfDTO booksBookShelfDTO) {
        booksBookShelfRepository.delete(booksBookShelfDTOMapper.apply(booksBookShelfDTO));
    }

    private User getCurrentUser() {
        String currentUsername = userAuthenticationService.getCurrentUsername();
        return userService.getUserByUsername(currentUsername);
    }
}
