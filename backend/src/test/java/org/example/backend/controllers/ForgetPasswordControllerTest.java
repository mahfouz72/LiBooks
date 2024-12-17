package org.example.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.models.dtos.UserDTO;
import org.example.backend.security.JWTService;
import org.example.backend.services.ForgetPasswordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ForgetPasswordController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ForgetPasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JWTService jwtService;
    @MockBean
    private ForgetPasswordService forgetPasswordService;

    @Test
    void testForgetPasswordWithValidEmail() throws Exception {
        // Mock data
        UserDTO userDTO = new UserDTO(
                1,
                "username",
                "testEmail@gmail.com",
                null,
                LocalDate.now());
        String user = objectMapper.writeValueAsString(userDTO);
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Email sent successfully");
        when(forgetPasswordService.forgetPassword(Mockito.any())).thenReturn(mockResponse);

        this.mockMvc.perform(
                        post("/forgetPassword")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(user))
                .andExpect(status().isOk());
    }

    @Test
    void testForgetPasswordWithInvalidEmail() throws Exception {
        // Mock data
        UserDTO userDTO = new UserDTO(
                1,
                "username",
                "testEmail2@gmail.com",
                null,
                LocalDate.now());
        String user = objectMapper.writeValueAsString(userDTO);
        ResponseEntity<String> mockResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not found");
        when(forgetPasswordService.forgetPassword(Mockito.any())).thenReturn(mockResponse);

        this.mockMvc.perform(
                        post("/forgetPassword")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(user))
                .andExpect(status().isNotFound());
    }

}
