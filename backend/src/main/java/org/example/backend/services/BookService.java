package org.example.backend.services;

import org.example.backend.models.dtos.BookDTO;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.Review;
import org.example.backend.repositories.BookRepository;
import org.example.backend.services.mappers.BookDTOMapper;
import org.example.backend.services.mappers.BookListingDTOMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;

    private BookListingDTOMapper bookListingDTOMapper;

    private final BookDTOMapper bookDTOMapper;

    public BookService(BookDTOMapper bookDTOMapper, BookRepository bookRepository,
                       BookListingDTOMapper bookListingDTOMapper) {
        this.bookDTOMapper = bookDTOMapper;
        this.bookRepository = bookRepository;
        this.bookListingDTOMapper = bookListingDTOMapper;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<BookListingDTO> listBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream().map(bookListingDTOMapper).collect(Collectors.toList());
    }

    public List<BookListingDTO> getLatestBooks() {
        Pageable pageable = PageRequest.of(0, 10,
            Sort.by(Sort.Direction.DESC, "bookId"));

        return bookRepository.findAll(pageable)
                .stream().map(bookListingDTOMapper).collect(Collectors.toList());
    }
    public BookDTO getBookPageViewById(Integer bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        BookDTO bookDTO = null;
        if (book != null) {
            bookDTO = bookDTOMapper.apply(book);
        }
        return bookDTO;
    }

    public void addReview(Review review, Integer bookId) {
        Book reviewedBook = getBookById(bookId);
        if (reviewedBook != null) {
            reviewedBook.getReviews().add(review);
            reviewedBook.setRatingsCount(reviewedBook.getRatingsCount() + 1);
            saveBook(reviewedBook);
        }
    }

}
