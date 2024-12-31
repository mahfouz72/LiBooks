package org.example.backend.repositories;

import org.example.backend.models.entities.Book;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.example.backend.models.entities.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {

    @Query("SELECT r.book FROM Recommendation r WHERE r.user.id = :userId")
    List<Book> findAllByUserId(Integer userId);

}
