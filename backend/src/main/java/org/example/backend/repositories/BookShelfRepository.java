package org.example.backend.repositories;

import org.example.backend.models.entities.BookShelf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookShelfRepository extends JpaRepository<BookShelf, Integer> {
     Page<BookShelf> findByUserId(Integer userId, Pageable pageable);
}
