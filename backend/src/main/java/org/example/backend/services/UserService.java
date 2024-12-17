package org.example.backend.services;

import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
}
