package org.example.backend.controllers;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username")
    public String getUsername() {
        return userService.getCurrentUsername();
    }

    @GetMapping("/users/count")
    public Long getUsersCount() {
        return userService.getUsersCount();
    }

    @GetMapping("/users/all")
    public List<UserDTO> getAllUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/users/{id}")
    public UserDTO getUserById(
            @PathVariable Integer id
    ) {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public ResponseEntity<UserDTO> getUserByUsername(
            @RequestParam String username
    ) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Integer id,
            @RequestBody UserDTO userDTO
    ) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Integer id
    ) {
        return userService.deleteUser(id);
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUserByUsername(
            @RequestParam String username
    ) {
        return userService.deleteUserByUsername(username);
    }

}
