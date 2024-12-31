package org.example.backend.controllers;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void setup() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUsername() throws Exception {
        Mockito.when(userService.getCurrentUsername()).thenReturn("testUser");

        mockMvc.perform(get("/api/users/username"))
                .andExpect(status().isOk())
                .andExpect(content().string("testUser"));
    }

    @Test
    public void testGetUsersCount() throws Exception {
        Mockito.when(userService.getUsersCount()).thenReturn(ResponseEntity.ok(10L));

        mockMvc.perform(get("/api/users/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDTO> users = Arrays.asList(
                new UserDTO(1, "JohnDoe", "john@example.com", LocalDate.of(1990, 1, 1), LocalDate.now()),
                new UserDTO(2, "JaneDoe", "jane@example.com", LocalDate.of(1992, 2, 2), LocalDate.now())
        );
        Mockito.when(userService.getAllUsers(any())).thenReturn(users);

        mockMvc.perform(get("/api/users/all")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("JohnDoe"))
                .andExpect(jsonPath("$[1].username").value("JaneDoe"));
    }

    @Test
    public void testGetUserById() throws Exception {
        UserDTO user = new UserDTO(1, "JohnDoe", "john@example.com", LocalDate.of(1990, 1, 1), LocalDate.now());
        Mockito.when(userService.getUserById(anyInt())).thenReturn(ResponseEntity.ok(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("JohnDoe"));
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        UserDTO user = new UserDTO(1, "JohnDoe", "john@example.com", LocalDate.of(1990, 1, 1), LocalDate.now());
        Mockito.when(userService.getUserByUsername(anyString())).thenReturn(ResponseEntity.ok(user));

        mockMvc.perform(get("/api/users")
                        .param("username", "JohnDoe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("JohnDoe"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDTO userDTO = new UserDTO(1, "JohnDoe", "john@example.com", LocalDate.of(1990, 1, 1), LocalDate.now());
        Mockito.when(userService.updateUser(anyInt(), any(UserDTO.class)))
                .thenReturn(ResponseEntity.ok("User updated successfully"));

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"username\":\"JohnDoe\",\"email\":\"john@example.com\",\"dateOfBirth\":\"1990-01-01\",\"dateCreated\":\"2023-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Mockito.when(userService.deleteUser(anyInt()))
                .thenReturn(ResponseEntity.ok("User deleted successfully"));

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    public void testDeleteUserByUsername() throws Exception {
        Mockito.when(userService.deleteUserByUsername(anyString()))
                .thenReturn(ResponseEntity.ok("User deleted successfully"));

        mockMvc.perform(delete("/api/users")
                        .param("username", "JohnDoe"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }
}