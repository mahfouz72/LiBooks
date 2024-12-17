package org.example.backend.services;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.PasswordResetToken;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.PasswordResetTokenRepository;
import org.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgetPasswordService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Value("${support.mail}")
    private String supportEmail;
    @Value("${forgetPassowrd.mail.subject}")
    private String mailSubject;
    @Value("${forgetPassowrd.mail.text}")
    private String mailText;
    @Value("${forgetPassowrd.token.expiry}")
    private int tokenExpiry;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public ResponseEntity<String> forgetPassord(UserDTO userDTO) {

        Boolean emailSent = false;
        System.out.println(userDTO.email());
        User user = userRepository.findByEmail(userDTO.email()).orElse(null);
        if (user != null) {
            emailSent = sendEmail(user);
        }
        if (emailSent) {
            return ResponseEntity.status(HttpStatus.OK).body("Email sent successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not found");
        }
    }

    public ResponseEntity<String> resetPassword(
            String password,
            String token) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                                                                            .orElse(null);
        if (passwordResetToken != null) {
            if (passwordResetToken.getExpiryDate().isAfter(java.time.LocalDateTime.now())) {
                User user = passwordResetToken.getUser();
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                passwordResetTokenRepository.delete(passwordResetToken);
                return ResponseEntity.status(HttpStatus.OK).body("Password reset successfully");
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token is not found");
        }
    }

    public Boolean sendEmail(User user) {

        try {
            String resetLink = generateResetLink(user);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(supportEmail);
            msg.setTo(user.getEmail());
            msg.setSubject(mailSubject);
            msg.setText(mailText + "\n" + resetLink);
            javaMailSender.send(msg);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateResetLink(User user) {

        UUID uuid = UUID.randomUUID();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = currentTime.plusMinutes(tokenExpiry);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(uuid.toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(expiryTime);

        PasswordResetToken token = passwordResetTokenRepository.save(passwordResetToken);
        if (token != null) {
            String endpointUrl = "http://localhost:3000/resetPassword";
            return  endpointUrl + "/" + passwordResetToken.getToken();
        }
        return null;
    }

}
