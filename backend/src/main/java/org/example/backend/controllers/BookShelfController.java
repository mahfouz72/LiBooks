package org.example.backend.controllers;

import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.dtos.BookShelfDTO;
import org.example.backend.models.dtos.BooksBookShelfDTO;
import org.example.backend.services.BookShelfService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BookShelf")
public class BookShelfController {
    private BookShelfService bookShelfService;

    public BookShelfController(BookShelfService bookShelfService) {
        this.bookShelfService = bookShelfService;
    }

    @GetMapping("/names")
    public List<BookShelfDTO> getBookShelfNames(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return bookShelfService.getBookShelfNames(pageable);
    }

    @PostMapping("/add")
    public ResponseEntity<BookShelfDTO> addBookShelf(@RequestBody BookShelfDTO bookShelfDTO) {
        return ResponseEntity.ok(bookShelfService.addBookShelf(bookShelfDTO));
    }

    @PutMapping("/rename/{bookShelfId}")
    public ResponseEntity<?> renameBookShelf(
        @PathVariable("bookShelfId") int bookShelfId,
        @RequestBody BookShelfDTO bookShelfDTO) {

        return bookShelfService.renameBookShelf(bookShelfId, bookShelfDTO);
    }

    @DeleteMapping("/delete/{bookShelfId}")
    public ResponseEntity<?> deleteBookShelf(@PathVariable int bookShelfId) {
        bookShelfService.deleteBookShelf(bookShelfId);
        return ResponseEntity.ok("BookShelf is deleted");
    }

    @GetMapping("/Books")
    public ResponseEntity<List<BookListingDTO>> getBooksInBookShelf(
        @RequestParam Integer bookShelfId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookShelfService.getBooksInBookShelf(bookShelfId, pageable));
    }

    @PostMapping("/Books/add")
    public ResponseEntity<BookListingDTO> addBookInBookShelf(
        @RequestBody BooksBookShelfDTO booksBookShelfDTO) {

        return ResponseEntity.ok(bookShelfService.addBookInBookShelf(booksBookShelfDTO));
    }

    @DeleteMapping("/Books/delete")
    public ResponseEntity<?> deleteBookInBookShelf(
        @RequestBody BooksBookShelfDTO booksBookShelfDTO) {

        bookShelfService.deleteBookInBookShelf(booksBookShelfDTO);
        return ResponseEntity.ok("Book is deleted from Book shelf");
    }

}
