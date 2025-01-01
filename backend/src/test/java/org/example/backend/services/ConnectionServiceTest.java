package org.example.backend.services;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.Connection;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.ConnectionsRepository;
import org.example.backend.services.mappers.UserDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceTest {

    @Mock
    private ConnectionsRepository connectionsRepository;
    @Mock
    private UserService userService;
    @Mock
    private UserDTOMapper userDTOMapper;

    private ConnectionService connectionService;
    private User user1;
    private User user2;
    private UserDTO userDTO1;
    private UserDTO userDTO2;

    @BeforeEach
    void setUp() {
        connectionService = new ConnectionService(connectionsRepository, userService, userDTOMapper);

        user1 = User.builder()
                .id(1)
                .username("user1")
                .email("user1@test.com")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        user2 = User.builder()
                .id(2)
                .username("user2")
                .email("user2@test.com")
                .dateOfBirth(LocalDate.of(1991, 1, 1))
                .build();

        userDTO1 = new UserDTO(1, "user1", "user1@test.com",
                LocalDate.of(1990, 1, 1), LocalDate.now());
        userDTO2 = new UserDTO(2, "user2", "user2@test.com",
                LocalDate.of(1991, 1, 1), LocalDate.now());
    }

    @Test
    void followUser_Success() {
        when(userService.getUserByUsername("user1")).thenReturn(user1);
        when(userService.getUserByUsername("user2")).thenReturn(user2);
        when(connectionsRepository.existsByFollowerAndFollowing(user2, user1)).thenReturn(false);

        assertDoesNotThrow(() -> connectionService.followUser("user1", "user2"));
        verify(connectionsRepository).save(any());
    }

    @Test
    void followUser_SelfFollow_ThrowsException() {
        assertThrows(IllegalStateException.class,
                () -> connectionService.followUser("user1", "user1"));
        verify(connectionsRepository, never()).save(any());
    }

    @Test
    void followUser_AlreadyFollowing_ThrowsException() {
        when(userService.getUserByUsername("user1")).thenReturn(user1);
        when(userService.getUserByUsername("user2")).thenReturn(user2);
        when(connectionsRepository.existsByFollowerAndFollowing(user2, user1)).thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> connectionService.followUser("user1", "user2"));
        verify(connectionsRepository, never()).save(any());
    }

    @Test
    void unfollowUser_Success() {
        when(userService.getUserByUsername("user1")).thenReturn(user1);
        when(userService.getUserByUsername("user2")).thenReturn(user2);
        when(connectionsRepository.existsByFollowerAndFollowing(user2, user1)).thenReturn(true);

        assertDoesNotThrow(() -> connectionService.unfollowUser("user1", "user2"));
        verify(connectionsRepository).deleteByFollowerAndFollowing(user2, user1);
    }

    @Test
    void unfollowUser_NonexistentConnection_ThrowsException() {
        when(userService.getUserByUsername("user1")).thenReturn(user1);
        when(userService.getUserByUsername("user2")).thenReturn(user2);
        when(connectionsRepository.existsByFollowerAndFollowing(user2, user1)).thenReturn(false);

        assertThrows(IllegalStateException.class,
                () -> connectionService.unfollowUser("user1", "user2"));
        verify(connectionsRepository, never()).deleteByFollowerAndFollowing(any(), any());
    }

    @Test
    void getAllFollowers_Success() {
        when(userService.getUserByUsername("user1")).thenReturn(user1);
        when(connectionsRepository.findByFollowing(user1))
                .thenReturn(Arrays.asList(mock(Connection.class)));
        when(userDTOMapper.apply(any())).thenReturn(userDTO2);

        ResponseEntity<List<UserDTO>> response = connectionService.getAllFollowers("user1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals(userDTO2, response.getBody().get(0));
    }

    @Test
    void getAllFollowings_Success() {
        when(userService.getUserByUsername("user1")).thenReturn(user1);
        when(connectionsRepository.findByFollower(user1))
                .thenReturn(Arrays.asList(mock(Connection.class)));
        when(userDTOMapper.apply(any())).thenReturn(userDTO2);

        ResponseEntity<List<UserDTO>> response = connectionService.getAllFollowings("user1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals(userDTO2, response.getBody().get(0));
    }

    @Test
    void getNumberOfFollowers_Success() {
        when(userService.getUserByUsername("user1")).thenReturn(user1);
        when(connectionsRepository.countFollowers(user1)).thenReturn(5);

        ResponseEntity<Integer> response = connectionService.getNumberOfFollowers("user1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(5, response.getBody());
    }

    @Test
    void getNumberOfFollowings_Success() {
        when(userService.getUserByUsername("user1")).thenReturn(user1);
        when(connectionsRepository.countFollowing(user1)).thenReturn(3);

        ResponseEntity<Integer> response = connectionService.getNumberOfFollowings("user1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(3, response.getBody());
    }
}