package org.example.backend.services.mappers;

import org.example.backend.models.dtos.BookDTO;
import org.example.backend.models.entities.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookDTOMapper implements Function<Book, BookDTO> {

    @Override
    public BookDTO apply(Book book) {
        return new BookDTO(
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
        );
    }
}
