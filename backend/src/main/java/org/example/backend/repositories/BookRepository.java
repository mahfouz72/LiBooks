package org.example.backend.repositories;

import org.example.backend.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // Query methods to be added here
    List<Book> findByBookTitleContainingIgnoreCase(String title);

    Book findByIsbn(String isbn);
}
