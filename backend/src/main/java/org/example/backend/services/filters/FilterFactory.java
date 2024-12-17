package org.example.backend.services.filters;

import org.example.backend.repositories.AuthorRepository;
import org.example.backend.repositories.BookRepository;
import org.example.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class FilterFactory {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    public FilterFactory(BookRepository bookRepository,
                         AuthorRepository authorRepository,
                         UserRepository bookwormRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = bookwormRepository;
    }

    public SearchFilter getFilter(String category) {
        switch (category.toLowerCase()) {
            case "books":
                return new BookFilter(bookRepository);
            case "users":
                return new UserFilter(userRepository);
            case "authors":
                return new AuthorFilter(authorRepository);
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }
}
