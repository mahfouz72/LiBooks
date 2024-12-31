package org.example.backend.services;

import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserAuthenticationService userAuthenticationService;

    public UserService(UserRepository userRepository,
                       UserAuthenticationService userAuthenticationService) {
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
    }

    public String getCurrentUsername() {
        return userAuthenticationService.getCurrentUsername();
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(getCurrentUsername()).orElse(null);
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
        return ResponseEntity.ok("Email is unique and could be registered.");
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
