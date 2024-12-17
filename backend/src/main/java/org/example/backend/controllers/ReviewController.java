package org.example.backend.controllers;

import org.example.backend.models.dtos.ReviewDTO;
import org.example.backend.models.entities.Review;
import org.example.backend.services.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ReviewDTO addReview(@RequestBody Review review, @RequestParam Integer bookId) {
        return reviewService.addReview(review, bookId);
    }

    @GetMapping("{bookId}")
    public List<ReviewDTO> findAllByBookId(@PathVariable Integer bookId) {
        return reviewService.findAllByBookId(bookId);
    }
}
