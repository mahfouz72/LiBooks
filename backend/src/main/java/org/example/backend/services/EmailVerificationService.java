package org.example.backend.services;

import org.example.backend.domain.VerificationCode;
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

    private final JavaMailSender mailSender;
    private final Map<String, VerificationCode> verificationMap;
    public EmailVerificationService(JavaMailSender mailSender){
        this.mailSender = mailSender;
        this.verificationMap = new HashMap<>();
    }
    @Async
    public void sendVerificationCode(EmailDTO emailDTO){
        String email = emailDTO.email();
        String code = generateSixDigitCode();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);
        VerificationCode verificationCode = new VerificationCode(code,expirationDate);
        verificationMap.put(email,verificationCode);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("LiBooks Support <alyeldin.mohamed.elsayed632@gmail.com>");
        message.setTo(email);
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + verificationCode.getCode());
        mailSender.send(message);
    }
    private String generateSixDigitCode(){
        return String.format("%06d", new Random().nextInt(1000000));
    }
    public ResponseEntity<String> checkVerificationCode(VerificationDTO verificationDTO){
        String email = verificationDTO.email();
        String sentCode=  verificationDTO.code();

        VerificationCode verificationCode = verificationMap.get(email);
        System.out.println("Correct code is: "+verificationCode.getCode());
        if(verificationCode.checkVerificationCode(sentCode)){
            return ResponseEntity.ok("Email is verified");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid verification code or code is expired");
    }
}
