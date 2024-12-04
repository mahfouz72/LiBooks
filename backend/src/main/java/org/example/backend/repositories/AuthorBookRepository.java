package org.example.backend.repositories;

import org.springframework.stereotype.Repository;
import org.example.backend.models.entities.AuthorBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.backend.models.entities.compositeKeys.AuthorBookID;

@Repository
public interface AuthorBookRepository extends JpaRepository<AuthorBook, AuthorBookID> {
    // Query methods to be added here
}
