package org.example.backend.controllers;

import org.example.backend.models.dtos.AuthorDTO;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.services.AuthorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add-author")
    public ResponseEntity<?> addAuthor(
            @RequestBody AuthorDTO authorDTO
    ) {
        return authorService.addAuthor(authorDTO);
    }

    @GetMapping("/count")
    public Long getAuthorsCount() {
        return authorService.getAuthorsCount();
    }

    @PostMapping("/all")
    public List<AuthorDTO> getAllAuthors(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return authorService.getAllAuthors(pageable);
    }

    @PostMapping("/names")
    public List<AuthorDTO> getAuthorsNames(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return authorService.getAuthorsNames(pageable);
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/{id}/books-ids")
    public List<Integer> getAuthorBooksIds(@PathVariable Integer id) {
        return authorService.getAuthorBooksIds(id);
    }

    @PostMapping("/books")
    public List<BookListingDTO> getAuthorBooks(
            @RequestParam Integer id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return authorService.getAuthorBooks(id, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuthor(
            @RequestBody AuthorDTO authorDTO
    ) {
        return authorService.updateAuthor(authorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Integer id) {
        return authorService.deleteAuthor(id);
    }
}
