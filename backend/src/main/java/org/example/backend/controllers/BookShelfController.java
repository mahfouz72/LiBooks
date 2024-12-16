package org.example.backend.controllers;

import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.models.entities.BookShelf;
import org.example.backend.models.entities.BooksBookShelf;
import org.example.backend.services.BookShelfService;
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
    public List<String> getBookShelfNames(@RequestParam Integer userId) {
        return bookShelfService.getBookShelfNames(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<BookShelf> addBookShelf(@RequestBody BookShelf bookShelf) {
        return ResponseEntity.ok(bookShelfService.addBookShelf(bookShelf));
    }

    @PutMapping("/rename/{bookShelfId}")
    public ResponseEntity<BookShelf> renameBookShelf(
        @PathVariable("bookShelfId") int bookShelfId,
        @RequestParam("newBookShelfName") String newBookShelfName) {

        BookShelf bookShelf = bookShelfService.renameBookShelf(bookShelfId, newBookShelfName);
        return ResponseEntity.ok(bookShelf);
    }

    @DeleteMapping("/delete/{bookShelfId}")
    public ResponseEntity<?> deleteBookShelf(@PathVariable int bookShelfId) {
        bookShelfService.deleteBookShelf(bookShelfId);
        return ResponseEntity.ok("BookShelf is deleted");
    }

    @GetMapping("/Books")
    public ResponseEntity<List<BookListingDTO>> getBooksInBookShelf(
        @RequestParam Integer bookShelfId) {

        return ResponseEntity.ok(bookShelfService.getBooksInBookShelf(bookShelfId));
    }

    @PostMapping("/Books/add")
    public ResponseEntity<BooksBookShelf> addBookInBookShelf(
        @RequestBody BooksBookShelf booksBookShelf) {

        return ResponseEntity.ok(bookShelfService.addBookInBookShelf(booksBookShelf));
    }

    @DeleteMapping("/Books/delete")
    public ResponseEntity<?> deleteBookInBookShelf(
        @RequestBody BooksBookShelf booksBookShelf) {

        bookShelfService.deleteBookInBookShelf(booksBookShelf);
        return ResponseEntity.ok("Book is deleted from Book shelf");
    }

}
