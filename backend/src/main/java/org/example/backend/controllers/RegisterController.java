package org.example.backend.controllers;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.dtos.UserRegistrationDTO;
import org.example.backend.services.UserAuthenticationService;
import org.example.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/register")
public class RegisterController {

    private UserAuthenticationService userAuthenticationService;
    private UserService userService;

    public RegisterController(UserAuthenticationService userAuthenticationService,
                              UserService userService) {
        this.userAuthenticationService = userAuthenticationService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserDTO userDTO = userAuthenticationService.register(userRegistrationDTO);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/request")
    public ResponseEntity<String> requestRegister(@RequestParam String email) {
        return userService.verifyUserExistenceByGmail(email);
    }
}
