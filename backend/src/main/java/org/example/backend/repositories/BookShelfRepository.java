package org.example.backend.repositories;

import org.example.backend.models.entities.BookShelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, Integer> {

    @Query(value = "SELECT book_shelf_name FROM book_shelf WHERE user_id = :userId",
           nativeQuery = true)
    List<String> findAllBookShelfNames(@Param("userId") Integer userId);

}
