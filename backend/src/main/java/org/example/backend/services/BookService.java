package org.example.backend.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.backend.models.entities.Book;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.backend.services.mappers.BookListingDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookListingDTOMapper bookDTOMapper;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }
    
    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<BookListingDTO> listBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
            .stream().map(bookDTOMapper).collect(Collectors.toList());
    }
}
