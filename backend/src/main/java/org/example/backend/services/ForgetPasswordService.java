package org.example.backend.services;

import org.example.backend.models.entities.PasswordResetToken;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgetPasswordService {

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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateResetLink(User user) {

        UUID uuid = UUID.randomUUID();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = currentTime.plusMinutes(15);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(uuid.toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(expiryTime);

        PasswordResetToken token = passwordResetTokenRepository.save(passwordResetToken);
        if (token != null) {
            String endpointUrl = "http://localhost:8080/resetPassword";
            return  endpointUrl + "/" + passwordResetToken.getToken();
        }
        return "";
    }
}
