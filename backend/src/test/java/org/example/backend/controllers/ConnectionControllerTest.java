package org.example.backend.controllers;


import org.example.backend.models.dtos.UserDTO;
import org.example.backend.services.ConnectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConnectionControllerTest {

    @Mock
    private ConnectionService connectionService;

    @InjectMocks
    private ConnectionController connectionController;

    private UserDTO userDTO1;
    private UserDTO userDTO2;

    @BeforeEach
    void setUp() {
        userDTO1 = new UserDTO(1, "UserOne", "user1@email.com", LocalDate.of(2020, 1, 8), LocalDate.of(2003, 1, 9));
        userDTO2 = new UserDTO(2, "UserTwo", "user2@email.com", LocalDate.of(2020, 2, 4), LocalDate.of(2006, 9, 12));
    }

    @Test
    void followUser_ShouldCallService() {
        connectionController.followUser("user1", "user2");
        verify(connectionService).followUser("user1", "user2");
    }

    @Test
    void unfollowUser_ShouldCallService() {
        connectionController.unfollowUser("user1", "user2");
        verify(connectionService).unfollowUser("user1", "user2");
    }

    @Test
    void getFollowers_ShouldReturnListOfUsers() {
        List<UserDTO> followers = Arrays.asList(userDTO1, userDTO2);
        when(connectionService.getAllFollowers("user1"))
                .thenReturn(ResponseEntity.ok(followers));

        ResponseEntity<List<UserDTO>> response = connectionController.getFollowers("user1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(connectionService).getAllFollowers("user1");
    }

    @Test
    void getFollowings_ShouldReturnListOfUsers() {
        List<UserDTO> followings = Arrays.asList(userDTO1, userDTO2);
        when(connectionService.getAllFollowings("user1"))
                .thenReturn(ResponseEntity.ok(followings));

        ResponseEntity<List<UserDTO>> response = connectionController.getFollowings("user1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(connectionService).getAllFollowings("user1");
    }

    @Test
    void getNumberOfFollowers_ShouldReturnCount() {
        when(connectionService.getNumberOfFollowers("user1"))
                .thenReturn(ResponseEntity.ok(5));

        ResponseEntity<Integer> response = connectionController.getNumberOfFollowers("user1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(5, response.getBody());
        verify(connectionService).getNumberOfFollowers("user1");
    }

    @Test
    void getNumberOfFollowings_ShouldReturnCount() {
        when(connectionService.getNumberOfFollowings("user1"))
                .thenReturn(ResponseEntity.ok(3));

        ResponseEntity<Integer> response = connectionController.getNumberOfFollowings("user1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(3, response.getBody());
        verify(connectionService).getNumberOfFollowings("user1");
    }
}
