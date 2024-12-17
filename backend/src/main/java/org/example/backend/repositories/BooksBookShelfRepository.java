package org.example.backend.repositories;

import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.BooksBookShelf;
import org.example.backend.models.entities.compositeKeys.BooksBookShelfId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface BooksBookShelfRepository extends JpaRepository<BooksBookShelf, BooksBookShelfId> {

    @Query("SELECT b "
        + "FROM BooksBookShelf bs JOIN bs.book b "
        + "WHERE bs.bookShelf.bookShelfId = :bookShelfId")
    Page<Book> findBooksByBookShelfId(@Param("bookShelfId") Integer bookShelfId, Pageable pageable);
}
