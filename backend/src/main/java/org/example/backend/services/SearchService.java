package org.example.backend.services;

import org.example.backend.repositories.AuthorRepository;
import org.example.backend.repositories.BookRepository;
import org.example.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.example.backend.models.dtos.SearchResultDTO;

import java.util.List;

@Service
public class SearchService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository bookwormRepository;

    public SearchService(BookRepository bookRepository,
                         AuthorRepository authorRepository,
                         UserRepository bookwormRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookwormRepository = bookwormRepository;
    }

    public List<SearchResultDTO> search(String category, String query) {
        switch (category.toLowerCase()) {
            case "books":
                return bookRepository.findByTitleContainingIgnoreCase(query)
                        .stream()
                        .map(book -> new SearchResultDTO(book.getTitle(), "Book"))
                        .collect(Collectors.toList());

            case "authors":
                return authorRepository.findByNameContainingIgnoreCase(query)
                        .stream()
                        .map(author -> new SearchResultDTO(author.getName(), "Author"))
                        .collect(Collectors.toList());

            case "users":
                return bookwormRepository.findByUsernameContainingIgnoreCase(query)
                        .stream()
                        .map(user -> new SearchResultDTO(user.getUsername(), "User"))
                        .collect(Collectors.toList());

            default:
                throw new IllegalArgumentException("Invalid category");
        }
    }
}
