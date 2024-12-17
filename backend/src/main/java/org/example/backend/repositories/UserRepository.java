package org.example.backend.repositories;

import org.example.backend.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsUserByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
