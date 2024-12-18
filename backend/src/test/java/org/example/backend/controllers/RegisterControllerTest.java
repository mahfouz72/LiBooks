package org.example.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.dtos.UserRegistrationDTO;
import org.example.backend.models.entities.User;
import org.example.backend.security.JWTService;
import org.example.backend.services.UserAuthenticationService;
import org.example.backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserAuthenticationService userAuthenticationService;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private UserDetailsManager userDetailsManager;


    @Test
    public void testValidRegister() throws Exception {
        UserRegistrationDTO user = new UserRegistrationDTO(
                "testUser", "testemail@gmail.com", "password");

        LocalDate dateCreated = LocalDate.now();

        String userAsJson = objectMapper.writeValueAsString(user);

        // mock the register method (assume it always works and return the correct result)
        when(userAuthenticationService.register(user)).thenReturn(
                new UserDTO(1, "testUser","testemail@gmail.com",
                        LocalDate.of(1990, 1, 1),
                        dateCreated
        ));

        this.mockMvc.perform(
                post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAsJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("testUser"))
        .andExpect(jsonPath("$.email").value("testemail@gmail.com"))
        .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
        .andExpect(jsonPath("$.dateCreated").value(dateCreated.toString()));
    }


}
