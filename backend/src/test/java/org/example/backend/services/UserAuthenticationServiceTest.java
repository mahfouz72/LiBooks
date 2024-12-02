package org.example.backend.services;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.dtos.UserRegistrationDTO;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.example.backend.security.JWTService;
import org.example.backend.services.mappers.UserDTOMapper;
import org.example.backend.services.mappers.UserRegistrationDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserAuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserDTOMapper userDTOMapper;

    @Mock
    private UserRegistrationDTOMapper userRegistrationDTOMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    private User user;
    private UserDTO userDTO;
    private UserRegistrationDTO userRegistrationDTO;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("testUser")
                .password("testpassword")
                .email("testemail@gmail.com")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        userRegistrationDTO =
                new UserRegistrationDTO("testUser", "testemail@gmail.com", "testpassword");

        userDTO = new UserDTO(1, "testUser", "testemail@gmail.com",
                LocalDate.of(1990, 1, 1), LocalDate.now());
    }

    @Test
    public void testRegister() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userDTOMapper.apply(any(User.class))).thenReturn(userDTO);
        when(userRegistrationDTOMapper.apply(any(UserRegistrationDTO.class))).thenReturn(user);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        UserDTO result = userAuthenticationService.register(userRegistrationDTO);

        assertEquals(userDTO, result);
    }

    @Test
    public void testLoginSuccess() {
        Authentication mockAuthentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);

        when(jwtService.generateToken(user.getUsername())).thenReturn("mockToken");

        ResponseEntity<String> response = userAuthenticationService.login(user.getUsername(), user.getPassword());

        assertEquals("mockToken", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetCurrentUserName() {
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurityContext = mock(SecurityContext.class);

        when(mockAuthentication.getName()).thenReturn("testUser");
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(mockSecurityContext);

            String username = userAuthenticationService.getCurrentUsername();
            assertEquals("testUser", username);
        }

    }
}