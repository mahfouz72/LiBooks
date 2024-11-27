package org.example.backend;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.example.backend.entities.Book;
import org.example.backend.services.BookService;

@SpringBootApplication
public class LiBooksApplication implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(LiBooksApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello, LiBooks!");
    
        // Fetch all books and print them
        List<Book> books = bookService.getAllBooks();
        for (Book book : books) {
            book.printBook();
        }
    }
}