package org.example.backend.controllers;

import org.example.backend.models.dtos.BookwormDTO;
import org.example.backend.security.JWTService;
import org.example.backend.services.BookwormService;
import org.example.backend.services.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookwormController {

    private final BookwormService bookwormService;
    private final UserAuthenticationService userAuthenticationService;

    public BookwormController(BookwormService bookwormService,
                              UserAuthenticationService userAuthenticationService) {
        this.bookwormService = bookwormService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @GetMapping("/username")
    public ResponseEntity<BookwormDTO> getProfile() {
        String currentUsername = userAuthenticationService.getCurrentUsername();
        BookwormDTO profile = bookwormService.getProfile(currentUsername);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/bookworms/{username}")
    public ResponseEntity<BookwormDTO> getBookwormProfile(
            @PathVariable String username) {
        String currentUsername = userAuthenticationService.getCurrentUsername();
        BookwormDTO profile = bookwormService.getBookwormProfile(username, currentUsername);
        return ResponseEntity.ok(profile);
    }
}

