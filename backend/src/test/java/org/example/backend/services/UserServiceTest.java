package org.example.backend.services;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.example.backend.services.mappers.UserDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDTOMapper userDTOMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("testUser")
                .password("testpassword")
                .email("testemail@gmail.com")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        userDTO = new UserDTO(1, "testUser", "testemail@gmail.com",
                LocalDate.of(1990, 1, 1), LocalDate.now());
    }

    @Test
    public void testRegister() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userDTOMapper.apply(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.register(user);

        assertEquals(userDTO, result);
    }
}