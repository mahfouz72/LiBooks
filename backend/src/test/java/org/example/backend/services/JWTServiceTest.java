package org.example.backend.services;

import org.example.backend.security.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @InjectMocks
    private JWTService jwtService;

    private String secretKey = "25df25d6cfbfc7c730099dd2d794fc9b426def76c3263dba81b850ed3982b377";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
    }



    @Test
    public void testExtractUsername() {

        String token = jwtService.generateToken("testUser");
        String username = jwtService.extractUsername(token);

        assertEquals("testUser", username);
    }

    @Test
    public void testIsValidToken() {
        UserDetails user = org.springframework.security.core.userdetails.User
                .withUsername("testUser")
                .password("testpassword")
                .authorities("USER")
                .build();

        String token = jwtService.generateToken("testUser");

        assertEquals(true, jwtService.isValidToken(token, user));
    }

}