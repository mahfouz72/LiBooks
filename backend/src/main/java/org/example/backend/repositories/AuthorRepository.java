package org.example.backend.repositories;

import org.example.backend.models.entities.Author;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    // Query methods to be added here
}
