package org.example.backend.controllers;

import org.example.backend.models.dtos.TokenDTO;
import org.example.backend.services.GmailValidationService;
import org.example.backend.services.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private UserAuthenticationService userAuthenticationService;
    private final GmailValidationService gmailValidationService;

    public LoginController(UserAuthenticationService userAuthenticationService, GmailValidationService gmailValidationService) {
        this.userAuthenticationService = userAuthenticationService;
        this.gmailValidationService = gmailValidationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        return userAuthenticationService.login(username, password);
    }

    @PostMapping("/login/google")
    public ResponseEntity<String> loginGoogle(@RequestBody TokenDTO tokenDTO){
        System.out.println(tokenDTO.token());
        String gmail = gmailValidationService.fetchGoogleEmail(tokenDTO.token());
        System.out.println("Gmail: "+gmail);
        return userAuthenticationService.loginByGmail(gmail);
    }

    @GetMapping("/test")
    public String getCurrentUsername() {
        return userAuthenticationService.getCurrentUsername();
    }
}
