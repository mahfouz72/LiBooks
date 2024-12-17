package org.example.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GmailValidationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private GmailValidationService gmailValidationService;

    private static final String ACCESS_TOKEN = "test-access-token";
    private static final String GOOGLE_API_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    @Test
    void fetchGoogleEmail_ValidResponse_ReturnsEmail() throws JsonProcessingException {
        // Arrange
        String responseBody = "{\"email\":\"test@gmail.com\"}";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);

        // Mock the RestTemplate exchange method
        when(restTemplate.exchange(
                eq(GOOGLE_API_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(mockResponse);

        // Mock the ObjectMapper to parse the response
        when(objectMapper.readTree(responseBody)).thenReturn(
                new ObjectMapper().readTree(responseBody)
        );

        String email = gmailValidationService.fetchGoogleEmail(ACCESS_TOKEN);

        assertEquals("test@gmail.com", email);

        verify(restTemplate).exchange(
                eq(GOOGLE_API_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        );
    }

    @Test
    void fetchGoogleEmail_RestClientException_ReturnsNull() {
        when(restTemplate.exchange(
                eq(GOOGLE_API_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenThrow(new RestClientException("Network error"));

        String email = gmailValidationService.fetchGoogleEmail(ACCESS_TOKEN);

        assertNull(email);

        verify(restTemplate).exchange(
                eq(GOOGLE_API_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        );
    }

    @Test
    void fetchGoogleEmail_JsonProcessingException_ReturnsNull() throws JsonProcessingException {
        // Arrange
        String responseBody = "{\"email\":\"test@gmail.com\"}";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);

        // Mock the RestTemplate exchange method
        when(restTemplate.exchange(
                eq(GOOGLE_API_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(mockResponse);

        // Mock the ObjectMapper to throw JsonProcessingException
        when(objectMapper.readTree(responseBody)).thenThrow(new JsonProcessingException("Parsing error") {});

        // Act
        String email = gmailValidationService.fetchGoogleEmail(ACCESS_TOKEN);

        // Assert
        assertNull(email);

        // Verify interactions
        verify(restTemplate).exchange(
                eq(GOOGLE_API_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        );
    }

    @Test
    void fetchGoogleEmail_HeaderAuthentication_VerifyBearerToken() {
        // Arrange
        String responseBody = "{\"email\":\"test@gmail.com\"}";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);

        // Capture the HttpEntity to verify headers
        when(restTemplate.exchange(
                eq(GOOGLE_API_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(mockResponse);

        // Mock the ObjectMapper
        try {
            when(objectMapper.readTree(responseBody)).thenReturn(
                    new ObjectMapper().readTree(responseBody)
            );
        } catch (JsonProcessingException e) {
            fail("Unexpected exception during test setup");
        }

        // Act
        gmailValidationService.fetchGoogleEmail(ACCESS_TOKEN);

        // Additional verification
        verify(restTemplate).exchange(
                eq(GOOGLE_API_URL),
                eq(HttpMethod.GET),
                argThat(argument -> {
                    // Verify that the HttpEntity has the correct Bearer token
                    HttpHeaders headers = argument.getHeaders();
                    return headers.getFirst(HttpHeaders.AUTHORIZATION) != null &&
                            headers.getFirst(HttpHeaders.AUTHORIZATION).equals("Bearer " + ACCESS_TOKEN);
                }),
                eq(String.class)
        );
    }
}