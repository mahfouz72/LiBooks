package org.example.backend.services;

import org.example.backend.models.dtos.BookDTO;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.entities.Author;
import org.example.backend.models.entities.AuthorBook;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.Review;
import org.example.backend.models.entities.compositeKeys.AuthorBookID;
import org.example.backend.repositories.AuthorRepository;
import org.example.backend.repositories.BookRepository;
import org.example.backend.repositories.AuthorBookRepository;
import org.example.backend.services.mappers.BookDTOMapper;
import org.example.backend.services.mappers.BookListingDTOMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;

    private BookListingDTOMapper bookListingDTOMapper;

    private final BookDTOMapper bookDTOMapper;
    private final AuthorBookRepository authorBookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookDTOMapper bookDTOMapper, BookRepository bookRepository,
                       BookListingDTOMapper bookListingDTOMapper,
                       AuthorBookRepository authorBookRepository,
                       AuthorRepository authorRepository) {
        this.bookDTOMapper = bookDTOMapper;
        this.bookRepository = bookRepository;
        this.bookListingDTOMapper = bookListingDTOMapper;
        this.authorBookRepository = authorBookRepository;
        this.authorRepository = authorRepository;
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
        // get 10 latest added book
        final int pageSize = 10;
        Pageable pageable = PageRequest.of(0, pageSize,
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
            saveBook(reviewedBook);
        }
    }

    public Long getBooksCount() {
        return bookRepository.count();
    }

    /**
     * Adds a new book to the repository.
     *
     * @param bookDTO the data transfer object containing book details
     * @return a ResponseEntity with a success message
     */
    public ResponseEntity<String> addBook(BookDTO bookDTO) {
        Book existingBook = bookRepository.findByIsbn(bookDTO.isbn());
        if (existingBook != null) {
            return ResponseEntity.badRequest().body("Book already exists");
        }
        Book book = Book.builder()
                .bookTitle(bookDTO.bookTitle())
                .isbn(bookDTO.isbn())
                .ratingsCount(0)
                .rating(new BigDecimal(0))
                .summary(bookDTO.summary())
                .bookCover(bookDTO.bookCover())
                .languageOfOrigin(bookDTO.languageOfOrigin())
                .publicationDate(bookDTO.publicationDate())
                .publisher(bookDTO.publisher())
                .genre(bookDTO.genre())
                .build();

        List<Author> authors = bookDTO.authors().stream()
                .map(authorRepository::findByAuthorName)
                .toList();

        for (Author author : authors) {
            if (author == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Some authors not found");
            }
        }

        bookRepository.save(book);
        authors.forEach(author -> authorBookRepository.save(new AuthorBook(
                new AuthorBookID(author.getAuthorId(), book.getBookId()),
                author,
                book)));
        return ResponseEntity.ok("Book added successfully");
    }

    public ResponseEntity<String> updateBook(Integer id, BookDTO bookDTO) {
        // To be implemented later
        return ResponseEntity.ok("Book updated successfully");
    }

    public ResponseEntity<String> deleteBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book != null) {
            bookRepository.delete(book);
            return ResponseEntity.ok("Book deleted successfully");
        }
        else {
            return ResponseEntity.badRequest().body("Book not found");
        }
    }
}
