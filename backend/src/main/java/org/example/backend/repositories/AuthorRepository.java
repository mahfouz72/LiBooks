package org.example.backend.repositories;

import org.example.backend.models.entities.Author;
import org.example.backend.models.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    // Query methods to be added here
    List<Author> findByAuthorNameContainingIgnoreCase(String name);

    @Query("SELECT a.authorName FROM Author a")
    List<String> getAuthorsNames();

    @Query("SELECT ab.book.bookId FROM AuthorBook ab JOIN ab.author a WHERE a.authorId = :id")
    List<Integer> getAuthorBooksIds(@Param("id") Integer id);

    @Query("SELECT b FROM AuthorBook ab JOIN ab.book b WHERE ab.author.authorId = :id")
    Page<Book> getAuthorBooks(Integer id, Pageable pageable);
}
