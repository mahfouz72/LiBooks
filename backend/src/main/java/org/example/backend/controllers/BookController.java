package org.example.backend.controllers;

import org.example.backend.models.dtos.BookDTO;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.services.BookService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public List<BookListingDTO> getAllBooks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookService.listBooks(pageable);
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Integer id) {
        return bookService.getBookPageViewById(id);
    }

    @GetMapping("/count")
    public Long getBooksCount() {
        return bookService.getBooksCount();
    }

    @PostMapping("/add-book")
    public ResponseEntity<String> addBook(@RequestBody BookDTO bookDTO) {
        System.out.println(bookDTO);
        return bookService.addBook(bookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(
            @PathVariable Integer id,
            @RequestBody BookDTO bookDTO
    ) {
        return bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping("/delete-by-isbn")
    public ResponseEntity<String> deleteBookByIsbn(
            @RequestParam String isbn
    ) {
        return bookService.deleteBookByIsbn(isbn);
    }
}
