package org.example.backend.controllers;

import org.example.backend.models.dtos.TokenDTO;
import org.example.backend.services.GmailValidationService;
import org.example.backend.services.UserAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private UserAuthenticationService userAuthenticationService;

    @Mock
    private GmailValidationService gmailValidationService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnResponseEntity_WhenCredentialsAreCorrect() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Login Successful", HttpStatus.OK);

        when(userAuthenticationService.login(username, password)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> actualResponse = loginController.login(username, password);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(userAuthenticationService, times(1)).login(username, password);
    }

    @Test
    void loginGoogle_ShouldReturnResponseEntity_WhenTokenIsValid() {
        // Arrange
        String token = "mockGoogleToken";
        String gmail = "test@gmail.com";
        TokenDTO tokenDTO = new TokenDTO(token);
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Google Login Successful", HttpStatus.OK);

        when(gmailValidationService.fetchGoogleEmail(token)).thenReturn(gmail);
        when(userAuthenticationService.loginByGmail(gmail)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> actualResponse = loginController.loginGoogle(tokenDTO);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(gmailValidationService, times(1)).fetchGoogleEmail(token);
        verify(userAuthenticationService, times(1)).loginByGmail(gmail);
    }

    @Test
    void getCurrentUsername_ShouldReturnUsername() {
        // Arrange
        String expectedUsername = "testUser";

        when(userAuthenticationService.getCurrentUsername()).thenReturn(expectedUsername);

        // Act
        String actualUsername = loginController.getCurrentUsername();

        // Assert
        assertNotNull(actualUsername);
        assertEquals(expectedUsername, actualUsername);

        verify(userAuthenticationService, times(1)).getCurrentUsername();
    }
}
