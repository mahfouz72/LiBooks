package org.example.backend.repositories;

import jakarta.transaction.Transactional;
import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.BookOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOfferRepository extends JpaRepository<BookOffer, Integer> {

    List<BookOffer> findAllByBook(Book book);

    @Transactional
    @Modifying
    @Query("DELETE FROM BookOffer bo WHERE bo.book.bookId = :bookId")
    void deleteAllByBookId(Integer bookId);
}
