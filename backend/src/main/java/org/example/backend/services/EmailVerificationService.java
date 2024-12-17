package org.example.backend.services;

import org.example.backend.models.VerificationCode;
import org.example.backend.models.dtos.EmailDTO;
import org.example.backend.models.dtos.VerificationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailVerificationService {

    private static final int CODE_EXPIRATION_MINUTES = 5;
    private static final int MAX_RANDOM_CODE = 1000000;

    private final JavaMailSender mailSender;
    private final Map<String, VerificationCode> verificationMap;

    public EmailVerificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.verificationMap = new HashMap<>();
    }

    /**
     * Sends a verification code to the provided email address.
     *
     * @param emailDTO Contains the email address to which the verification code will be sent.
     */
    @Async
    public void sendVerificationCode(EmailDTO emailDTO) {
        String email = emailDTO.email();
        String code = generateSixDigitCode();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES);
        VerificationCode verificationCode = new VerificationCode(code, expirationDate);
        verificationMap.put(email, verificationCode);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("LiBooks Support <alyeldin.mohamed.elsayed632@gmail.com>");
        message.setTo(email);
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + verificationCode.getCode());
        mailSender.send(message);
    }

    /**
     * Generates a six-digit random verification code.
     *
     * @return A six-digit string code.
     */
    public String generateSixDigitCode() {
        return String.format("%06d", new Random().nextInt(MAX_RANDOM_CODE));
    }

    /**
     *
     * @param verificationDTO
     * @return
     */
    public ResponseEntity<String> checkVerificationCode(VerificationDTO verificationDTO) {
        String email = verificationDTO.email();
        String sentCode = verificationDTO.code();

        VerificationCode verificationCode = verificationMap.get(email);
        System.out.println("Correct code is: " + verificationCode.getCode());

        String responseMessage;
        HttpStatus status;

        if (verificationCode.checkVerificationCode(sentCode)) {
            responseMessage = "Email is verified";
            status = HttpStatus.OK;
        }
        else {
            responseMessage = "Invalid verification code or code is expired";
            status = HttpStatus.UNAUTHORIZED;
        }
        return ResponseEntity.status(status).body(responseMessage);
    }

    public Map<String, VerificationCode> getVerificationMap() {
        return verificationMap;
    }
}
