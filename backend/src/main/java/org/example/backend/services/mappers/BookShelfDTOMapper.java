package org.example.backend.services.mappers;

import org.example.backend.models.dtos.BookShelfDTO;
import org.example.backend.models.entities.BookShelf;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BookShelfDTOMapper implements Function<BookShelf, BookShelfDTO> {

    @Override
    public BookShelfDTO apply(BookShelf bookShelf) {
        return new BookShelfDTO(
            bookShelf.getBookShelfId(),
            bookShelf.getBookShelfName()
        );
    }
}
