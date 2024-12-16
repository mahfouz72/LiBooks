package org.example.backend.controllers;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.example.backend.services.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ForgetPasswordController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ForgetPasswordService forgetPasswordService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @PostMapping("/forgetPassword")
    public String forgetPassword(@RequestBody UserDTO userDTO) {

        Boolean ok = false;
        User user = userRepository.findByEmail(userDTO.email()).orElseThrow(null);
        if (user != null) {
            ok = forgetPasswordService.sendEmail(user);
        }
        if (ok) {
            return "Email sent successfully";
        }
        else {
            return "Email is not found";
        }
    }

    @GetMapping("/testme")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test");
    }
}
