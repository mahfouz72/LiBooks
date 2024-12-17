package org.example.backend.services.filters;

import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.repositories.BookRepository;

import java.util.List;
import java.util.stream.Collectors;


public class BookFilter implements SearchFilter {

    private final BookRepository bookRepository;

    public BookFilter(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookListingDTO> applyFilter(String query) {
        return bookRepository.findByBookTitleContainingIgnoreCase(query)
                .stream()
                .map(book -> new BookListingDTO(book.getBookId(),
                        book.getBookTitle(), book.getRating(),
                        book.getBookCover(), book.getAuthors()))
                .collect(Collectors.toList());
    }
}
