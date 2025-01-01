package org.example.backend.repositories;

import org.example.backend.models.entities.Book;
import org.example.backend.models.entities.Review;
import org.example.backend.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllByBook(Book book);
    
    Review findByUserAndBook(User user, Book book);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
    List<Review> findAllByUserId(Integer userId);
}
