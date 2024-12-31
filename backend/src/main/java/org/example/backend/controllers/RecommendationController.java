package org.example.backend.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.example.backend.models.dtos.BookListingDTO;
import org.example.backend.services.RecommendationService;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/books")
    public List<BookListingDTO> getRecommendations() {
        return recommendationService.listRecommendations();
    }
}
