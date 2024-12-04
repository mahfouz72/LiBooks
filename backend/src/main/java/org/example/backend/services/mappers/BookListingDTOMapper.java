package org.example.backend.services.mappers;

import org.springframework.stereotype.Service;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.entities.Book;

import java.util.function.Function;

@Service
public class BookListingDTOMapper implements Function<Book, BookListingDTO> {
    
    @Override
    public BookListingDTO apply(Book book) {
        return new BookListingDTO(
                book.getBookId(),
                book.getBookTitle(),
                book.getRating(),
                book.getBookCover(),
                book.getAuthors()
        );
    }
}
