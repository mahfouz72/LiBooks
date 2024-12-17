package org.example.backend.services;

import org.example.backend.models.VerificationCode;
import org.example.backend.models.dtos.EmailDTO;
import org.example.backend.models.dtos.VerificationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailVerificationServiceTest {

    private EmailVerificationService emailVerificationService;
    private JavaMailSender mailSender;

    @BeforeEach
    void setUp() {
        mailSender = Mockito.mock(JavaMailSender.class);
        emailVerificationService = new EmailVerificationService(mailSender);
    }

    @Test
    void testSendVerificationCode() {
        EmailDTO emailDTO = new EmailDTO("test@example.com");

        // Mock mail sender behavior
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Call the method
        emailVerificationService.sendVerificationCode(emailDTO);

        // Verify the mail sender is called
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testGenerateSixDigitCode() {
        String code = emailVerificationService.generateSixDigitCode();
        assertNotNull(code);
        assertEquals(6, code.length());
        assertTrue(code.matches("\\d{6}"));
    }

    @Test
    void testCheckVerificationCode_Success() {
        String email = "test@example.com";
        String code = "123456";
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);
        VerificationCode verificationCode = new VerificationCode(code, expirationDate);

        // Add verification code to the map
        emailVerificationService.getVerificationMap().put(email, verificationCode);

        // Create DTO
        VerificationDTO verificationDTO = new VerificationDTO(email, code);

        // Call the method
        var response = emailVerificationService.checkVerificationCode(verificationDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Email is verified", response.getBody());
    }

    @Test
    void testCheckVerificationCode_Failure() {
        String email = "test@example.com";
        String code = "123456";
        LocalDateTime expirationDate = LocalDateTime.now().minusMinutes(5); // Expired code
        VerificationCode verificationCode = new VerificationCode(code, expirationDate);

        // Add verification code to the map
        emailVerificationService.getVerificationMap().put(email, verificationCode);

        // Create DTO with incorrect code
        VerificationDTO verificationDTO = new VerificationDTO(email, "654321");

        // Call the method
        var response = emailVerificationService.checkVerificationCode(verificationDTO);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid verification code or code is expired", response.getBody());
    }
}
