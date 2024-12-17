package org.example.backend.services;

import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByGmail_ShouldReturnUser_WhenEmailExists() {
        // Arrange
        String email = "test@gmail.com";
        User expectedUser = new User();
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email.toLowerCase())).thenReturn(Optional.of(expectedUser));

        // Act
        User actualUser = userService.getUserByGmail(email);

        // Assert
        assertNotNull(actualUser);
        assertEquals(email, actualUser.getEmail());

        verify(userRepository, times(1)).findByEmail(email.toLowerCase());
    }

    @Test
    void getUserByGmail_ShouldReturnNull_WhenEmailDoesNotExist() {
        // Arrange
        String email = "nonexistent@gmail.com";

        when(userRepository.findByEmail(email.toLowerCase())).thenReturn(Optional.empty());

        // Act
        User actualUser = userService.getUserByGmail(email);

        // Assert
        assertNull(actualUser);

        verify(userRepository, times(1)).findByEmail(email.toLowerCase());
    }

    @Test
    void verifyUserExistenceByGmail_ShouldReturnConflict_WhenEmailExists() {
        // Arrange
        String email = "existing@gmail.com";
        User existingUser = new User();
        existingUser.setEmail(email);

        when(userRepository.findByEmail(email.toLowerCase())).thenReturn(Optional.of(existingUser));

        // Act
        ResponseEntity<String> response = userService.verifyUserExistenceByGmail(email);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("This Email already exists! please log in", response.getBody());

        verify(userRepository, times(1)).findByEmail(email.toLowerCase());
    }

    @Test
    void verifyUserExistenceByGmail_ShouldReturnOk_WhenEmailIsUnique() {
        // Arrange
        String email = "unique@gmail.com";

        when(userRepository.findByEmail(email.toLowerCase())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = userService.verifyUserExistenceByGmail(email);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email is unique and could be registered", response.getBody());

        verify(userRepository, times(1)).findByEmail(email.toLowerCase());
    }
}
