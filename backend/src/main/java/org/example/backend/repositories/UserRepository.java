package org.example.backend.repositories;

import org.example.backend.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByUsername(String username);
}
