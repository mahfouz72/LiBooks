package org.example.backend.repositories;

import org.example.backend.models.entities.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // Query methods to be added here
    List<Book> findByBookTitleContainingIgnoreCase(String title);

    // get top 10 books based on popularity = rating * number of reviews
    @Query("SELECT b FROM Book b ORDER BY b.rating * b.ratingsCount DESC")
    List<Book> getTopRatedBooks(Pageable pageable);
}
