package org.example.backend.controllers;

import org.example.backend.models.dtos.EmailDTO;
import org.example.backend.models.dtos.VerificationDTO;
import org.example.backend.services.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/verification")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody EmailDTO emailDTO) {
        emailVerificationService.sendVerificationCode(emailDTO);
        return ResponseEntity.ok("Verification email sent");
    }

    @PostMapping("/code")
    public ResponseEntity<String> checkVerificationCode(
            @RequestBody VerificationDTO verificationDTO
    ) {
        return emailVerificationService.checkVerificationCode(verificationDTO);
    }
}
