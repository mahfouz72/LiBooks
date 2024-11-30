package org.example.backend.repositories;

import org.example.backend.entites.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {
}
