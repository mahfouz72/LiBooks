package org.example.backend.controllers;

import org.example.backend.models.dtos.ReviewDTO;
import org.example.backend.models.entities.Review;
import org.example.backend.security.JWTService;
import org.example.backend.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @MockBean
    private ReviewService reviewService;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private UserDetailsManager userDetailsManager;

    @Test
    public void testAddReview() throws Exception {
        Integer bookId = 1;
        when(reviewService.addReview(any(Review.class), eq(bookId)))
                .thenReturn(new ReviewDTO("mahfouz", "content", new BigDecimal(4)));

        mockMvc.perform(post("/reviews/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reviewText\":\"content\"}")
                        .param("bookId", bookId.toString()))
                .andExpect(content().json("{\"username\":\"mahfouz\",\"reviewText\":\"content\",\"rating\":4}"));
    }

    @Test
    public void testFindAllByBookId() throws Exception {
        Integer bookId = 1;
        List<ReviewDTO> reviewDTOList = Arrays.asList(
                new ReviewDTO("mahfouz", "content", new BigDecimal(4)),
                new ReviewDTO("mahfouz", "content", new BigDecimal(4)));

        when(reviewService.findAllByBookId(bookId)).thenReturn(reviewDTOList);

        mockMvc.perform(get("/reviews/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"username\":\"mahfouz\",\"reviewText\":\"content\",\"rating\":4}, " +
                        "{\"username\":\"mahfouz\",\"reviewText\":\"content\",\"rating\":4}]"));
    }
}