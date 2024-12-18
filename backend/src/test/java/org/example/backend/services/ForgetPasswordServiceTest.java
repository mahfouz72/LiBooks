package org.example.backend.services;

import org.example.backend.models.entities.PasswordResetToken;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.PasswordResetTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ForgetPasswordServiceTest {

    @InjectMocks
    private ForgetPasswordService forgetPasswordService;
    private final int tokenExpiry = 15;
    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateResetLink() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");

        PasswordResetToken mockToken = new PasswordResetToken();
        mockToken.setToken("mockToken");
        mockToken.setUser(user);
        mockToken.setExpiryDate(LocalDateTime.now().plusMinutes(tokenExpiry));

        when(passwordResetTokenRepository.save(any(PasswordResetToken.class))).thenReturn(mockToken);

        Method method = ForgetPasswordService.class.getDeclaredMethod("generateResetLink", User.class);
        method.setAccessible(true);

        String resetLink = (String) method.invoke(forgetPasswordService, user);

        assertNotNull(resetLink);
        assert(resetLink.contains("http://localhost:3000/resetPassword"));
    }
}