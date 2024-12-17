package org.example.backend.controllers;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.repositories.PasswordResetTokenRepository;
import org.example.backend.services.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ForgetPasswordController {


    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @PostMapping("/forgetPassword")
    public ResponseEntity<String> forgetPassword(@RequestBody UserDTO userDTO) {

        return forgetPasswordService.forgetPassord(userDTO);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String password,
                                                @RequestParam String token) {

        return forgetPasswordService.resetPassword(password, token);
    }

}
