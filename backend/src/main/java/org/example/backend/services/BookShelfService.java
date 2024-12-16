package org.example.backend.services;

import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.BookShelf;
import org.example.backend.models.entities.BooksBookShelf;
import org.example.backend.repositories.BookShelfRepository;
import org.example.backend.repositories.BooksBookShelfRepository;
import org.example.backend.services.mappers.BookListingDTOMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookShelfService {

    private BookShelfRepository bookShelfRepository;

    private BooksBookShelfRepository booksBookShelfRepository;

    public BookShelfService(BookShelfRepository bookShelfRepository,
                            BooksBookShelfRepository booksBookShelfRepository) {
        this.bookShelfRepository = bookShelfRepository;
        this.booksBookShelfRepository = booksBookShelfRepository;
    }

    public List<String> getBookShelfNames(Integer userId) {
        // TODO pageable
        return bookShelfRepository.findAllBookShelfNames(userId);
    }

    public BookShelf addBookShelf(BookShelf bookShelf) {
        return bookShelfRepository.save(bookShelf);
    }

    public BookShelf renameBookShelf(Integer bookShelfId, String newBookShelfName) {
        BookShelf bookShelf = bookShelfRepository.findById(bookShelfId).orElse(null);
        if (bookShelf != null) {
            bookShelf.setBookShelfName(newBookShelfName);
            bookShelfRepository.save(bookShelf);
        }
        return bookShelf;
    }

    public void deleteBookShelf(Integer bookShelfId) {
        bookShelfRepository.deleteById(bookShelfId);
    }

    public List<BookListingDTO> getBooksInBookShelf(Integer bookShelfId) {
        // TODO Pageable
        BookListingDTOMapper bookDTOMapper = new BookListingDTOMapper();
        List<Book> books = booksBookShelfRepository.findBooksByBookShelfId(bookShelfId);
        return books.stream()
            .map(bookDTOMapper).collect(Collectors.toList());
    }

    public BooksBookShelf addBookInBookShelf(BooksBookShelf booksBookShelf) {
        // TODO return BooksBookShelf DTO
        return booksBookShelfRepository.save(booksBookShelf);
    }

    public void deleteBookInBookShelf(BooksBookShelf booksBookShelf) {
        booksBookShelfRepository.delete(booksBookShelf);
    }
}
