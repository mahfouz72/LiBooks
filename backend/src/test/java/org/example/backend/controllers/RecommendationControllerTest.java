package org.example.backend.controllers;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;
import org.example.backend.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.UserDetailsManager;

import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.services.RecommendationService;

@WebMvcTest(RecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private JWTService jwtService;
    
    @MockBean
    private UserDetailsManager userDetailsManager;
    
    @MockBean
    private RecommendationService recommendationService;

    @Test
    public void testGetRecommendations() throws Exception {

        List<BookListingDTO> recommendations = Arrays.asList(
            new BookListingDTO(
                1,
                "title 1",
                null,
                null,
                null),
            new BookListingDTO(
                2,
                "title 2",
                null,
                null,
                null)
        );

        when(recommendationService.listRecommendations())
                .thenReturn(recommendations);
   
        mockMvc.perform(get("/recommendations/books")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                org.springframework.test.web.servlet.result.MockMvcResultMatchers.content()
                .json("[{\"bookId\":1,\"bookTitle\":\"title 1\"},{\"bookId\":2,\"bookTitle\":\"title 2\"}]")
            );
    }

}
