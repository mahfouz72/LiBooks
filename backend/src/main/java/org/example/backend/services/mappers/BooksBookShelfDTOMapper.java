package org.example.backend.services.mappers;

import org.example.backend.models.dtos.BooksBookShelfDTO;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.BookShelf;
import org.example.backend.models.entities.BooksBookShelf;
import org.example.backend.models.entities.compositeKeys.BooksBookShelfId;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BooksBookShelfDTOMapper implements Function<BooksBookShelfDTO, BooksBookShelf> {

    @Override
    public BooksBookShelf apply(BooksBookShelfDTO booksBookShelfDTO) {
        Integer bookShelfId = booksBookShelfDTO.bookShelfId();

        Integer bookId = booksBookShelfDTO.bookId();
        BooksBookShelfId booksBookShelfId = new BooksBookShelfId(
            bookShelfId,
            bookId
        );

        return BooksBookShelf.builder()
            .booksBookShelfId(booksBookShelfId)
            .bookShelf(BookShelf.builder()
                .bookShelfId(bookShelfId)
                .build())
            .book(Book.builder()
                .bookId(bookId)
                .build())
            .build();
    }
}
