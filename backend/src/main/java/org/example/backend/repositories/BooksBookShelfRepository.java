package org.example.backend.repositories;

import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.BooksBookShelf;
import org.example.backend.models.entities.compositeKeys.BooksBookShelfId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksBookShelfRepository extends JpaRepository<BooksBookShelf, BooksBookShelfId> {

    @Query("SELECT b "
        + "FROM BooksBookShelf bs JOIN bs.book b "
        + "WHERE bs.bookShelf.bookShelfId = :bookShelfId")
    List<Book> findBooksByBookShelfId(@Param("bookShelfId") Integer bookShelfId);
}
