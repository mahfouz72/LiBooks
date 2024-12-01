package org.example.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.User;
import org.example.backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testValidRegister() throws Exception {
        User user = User.builder()
                .username("testUser")
                .password("testpassword")
                .email("testemail@gmail.com")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        LocalDate dateCreated = LocalDate.now();

        String userAsJson = objectMapper.writeValueAsString(user);

        // mock the register method (assume it always works and return the correct result)
        when(userService.register(user)).thenReturn(
                new UserDTO(1, "testUser","testemail@gmail.com",
                        LocalDate.of(1990, 1, 1),
                        dateCreated
        ));

        this.mockMvc.perform(
                post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAsJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("testUser"))
        .andExpect(jsonPath("$.email").value("testemail@gmail.com"))
        .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
        .andExpect(jsonPath("$.dateCreated").value(dateCreated.toString()));
    }


}
