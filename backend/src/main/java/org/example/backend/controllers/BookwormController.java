package org.example.backend.controllers;

import org.example.backend.models.dtos.BookwormDTO;
import org.example.backend.security.JWTService;
import org.example.backend.services.BookwormService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookwormController {

    private final BookwormService bookwormService;
    private final JWTService jwtService;

    public BookwormController(BookwormService bookwormService, JWTService jwtService) {
        this.bookwormService = bookwormService;
        this.jwtService = jwtService;
    }

    @GetMapping("/username")
    public ResponseEntity<BookwormDTO> getProfile(
            @RequestHeader("Authorization") String token) {
        token = token.replaceAll("Bearer ", "");
        String currentUsername = jwtService.extractUsername(token);
        BookwormDTO profile = bookwormService.getProfile(currentUsername);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<BookwormDTO> getBookwormProfile(
            @PathVariable String username,
            @RequestHeader("Authorization") String token) {
        token = token.replaceAll("Bearer ", "");
        String currentUsername = jwtService.extractUsername(token);
        BookwormDTO profile = bookwormService.getBookwormProfile(username, currentUsername);
        return ResponseEntity.ok(profile);
    }
}

