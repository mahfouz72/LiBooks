package org.example.backend.controllers;

import org.example.backend.services.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private UserAuthenticationService userAuthenticationService;

    public LoginController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        return userAuthenticationService.login(username, password);
    }

    @GetMapping("/test")
    public String getCurrentUsername() {
        return userAuthenticationService.getCurrentUsername();
    }
}
