package org.example.backend.controllers;

import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.entities.Book;
import org.example.backend.services.BookService;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public List<BookListingDTO> getAllBooks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page,size);
        return bookService.listBooks(pageable);
    }

}
