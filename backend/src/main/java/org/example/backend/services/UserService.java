package org.example.backend.services;

import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByGmail(String email) {
        return userRepository.findByEmail(email.toLowerCase()).orElse(null);
    }

    public ResponseEntity<String> verifyUserExistenceByGmail(String email) {
        Optional<User> user = userRepository.findByEmail(email.toLowerCase());

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This Email already exists! Please log in.");
        }

        // If the email is unique and does not exist in the system
        return ResponseEntity.ok("Email is unique and could be registered.");
    }
}
