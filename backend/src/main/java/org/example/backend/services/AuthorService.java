package org.example.backend.services;

import org.example.backend.models.dtos.AuthorDTO;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.entities.Author;
import org.example.backend.models.entities.Book;
import org.example.backend.repositories.AuthorRepository;
import org.example.backend.services.mappers.AuthorDTOMapper;
import org.example.backend.services.mappers.BookListingDTOMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;
    private AuthorDTOMapper authorDTOMapper;
    private final String authorNotFound = "Author not found";

    public AuthorService(AuthorRepository authorRepository, AuthorDTOMapper authorDTOMapper) {
        this.authorRepository = authorRepository;
        this.authorDTOMapper = authorDTOMapper;
    }

    public ResponseEntity<String> addAuthor(AuthorDTO authorDTO) {
        Author existAuthor = authorRepository.findByAuthorName(authorDTO.authorName());
        if (existAuthor != null) {
            return ResponseEntity.badRequest().body("Author already exists");
        }
        Author author = Author.builder()
                .authorName(authorDTO.authorName())
                .authorPhoto(authorDTO.authorPhoto())
                .biography(authorDTO.authorBiography())
                .dateOfBirth(authorDTO.authorBirthDate())
                .nationality(authorDTO.nationality())
                .numberOfFollowers(0)
                .build();

        authorRepository.save(author);
        return ResponseEntity.ok("Author added successfully");
    }

    public List<AuthorDTO> getAllAuthors(Pageable pageable) {
        List<Author> author = authorRepository.findAll(pageable).toList();
        return author.stream()
                .map(authorDTOMapper)
                .toList();
    }

    public List<AuthorDTO> getAuthorsNames(Pageable pageable) {
        return authorRepository.getAuthorsNames(pageable);
    }

    public AuthorDTO getAuthorById(Integer id) {
        Author author = authorRepository.findById(id).orElse(null);
        assert author != null;
        return authorDTOMapper.apply(author);
    }

    public List<Integer> getAuthorBooksIds(Integer id) {
        return authorRepository.getAuthorBooksIds(id);
    }

    public ResponseEntity<String> updateAuthor(AuthorDTO authorDTO) {
        Author author = authorRepository.findById(authorDTO.authorId()).orElse(null);
        if (author == null) {
            return ResponseEntity.badRequest().body(authorNotFound);
        }
        author.setAuthorName(authorDTO.authorName());
        author.setAuthorPhoto(authorDTO.authorPhoto());
        author.setBiography(authorDTO.authorBiography());
        author.setDateOfBirth(authorDTO.authorBirthDate());
        author.setNationality(authorDTO.nationality());

        authorRepository.save(author);
        return ResponseEntity.ok("Author updated successfully");
    }

    public ResponseEntity<String> deleteAuthor(Integer id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return ResponseEntity.badRequest().body(authorNotFound);
        }
        authorRepository.delete(author);
        return ResponseEntity.ok("Author deleted successfully");
    }

    public List<BookListingDTO> getAuthorBooks(Integer id, Pageable pageable) {
        BookListingDTOMapper bookListingDTOMapper = new BookListingDTOMapper();
        Page<Book> books = authorRepository.getAuthorBooks(id, pageable);
        return books.stream()
                .map(bookListingDTOMapper).collect(Collectors.toList());
    }

    public Long getAuthorsCount() {
        return authorRepository.count();
    }
}
