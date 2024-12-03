package org.example.backend.controllers;

import org.example.backend.models.dtos.TokenDTO;
import org.example.backend.services.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private UserAuthenticationService userAuthenticationService;

    public LoginController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/login/basic")
    public ResponseEntity<String> basicLogin(@RequestParam String username,
                                        @RequestParam String password) {
        return userAuthenticationService.login(username, password);
    }

    @GetMapping("/test")
    public String getCurrentUsername() {
        return userAuthenticationService.getCurrentUsername();
    }
}
