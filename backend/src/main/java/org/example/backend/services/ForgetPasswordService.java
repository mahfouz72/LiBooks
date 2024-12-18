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
import org.springframework.mail.MailException;
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


    public ResponseEntity<String> forgetPassword(UserDTO userDTO) {

        ResponseEntity<String> response = null;
        Boolean emailSent = false;
        User user = userRepository.findByEmail(userDTO.email()).orElse(null);
        if (user != null) {
            emailSent = sendEmail(user);
        }
        if (emailSent) {
            response = ResponseEntity.status(HttpStatus.OK).body("Email sent successfully");
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not found");
        }
        return response;
    }

    /**
     * Resets the password for a user based on the provided token.
     *
     * @param password the new password to be set
     * @param token the token used to validate the password reset request
     * @return a ResponseEntity indicating the result of the password reset operation
     */
    public ResponseEntity<String> resetPassword(
            String password,
            String token) {

        ResponseEntity<String> response = null;
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token
                                                                                        .trim())
                                                                            .orElse(null);
        if (passwordResetToken != null) {
            if (passwordResetToken.getExpiryDate().isAfter(java.time.LocalDateTime.now())) {
                User user = passwordResetToken.getUser();
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                passwordResetTokenRepository.delete(passwordResetToken);
                response = ResponseEntity.status(HttpStatus.OK).body("Password reset successfully");
            }
            else {
                response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired");
            }
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token is not found");
        }
        return response;
    }

    /**
     * Sends a password reset email to the specified user.
     *
     * @param user the user to whom the password reset email will be sent
     * @return true if the email was sent successfully, false otherwise
     */
    private Boolean sendEmail(User user) {

        boolean emailSent = false;
        try {
            String resetLink = generateResetLink(user);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(supportEmail);
            msg.setTo(user.getEmail());
            msg.setSubject(mailSubject);
            msg.setText(mailText + "\n" + resetLink);
            javaMailSender.send(msg);

            emailSent = true;
        }
        /**
         * Handles exceptions that occur while sending the email.
         *
         * @param mailSendError the exception thrown during email sending
         */
        catch (MailException mailSendError) {
            mailSendError.printStackTrace();
        }
        return emailSent;
    }

    /**
     * Generates a password reset link for the given user.
     *
     * @param user the user for whom the reset link is generated
     * @return the generated reset link
     */
    private String generateResetLink(User user) {

        UUID uuid = UUID.randomUUID();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = currentTime.plusMinutes(tokenExpiry);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(uuid.toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(expiryTime);

        PasswordResetToken token = passwordResetTokenRepository.save(passwordResetToken);
        String endpointUrl = null;
        if (token != null) {
            endpointUrl = "http://localhost:3000/resetPassword";
            endpointUrl = endpointUrl + "/" + passwordResetToken.getToken();
        }
        return endpointUrl;
    }

}
