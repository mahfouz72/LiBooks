package org.example.backend.services.mappers;

import org.example.backend.models.dtos.ReviewDTO;
import org.example.backend.models.entities.Review;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReviewDTOMapper implements Function<Review, ReviewDTO> {
    @Override
    public ReviewDTO apply(Review review) {
        return new ReviewDTO(
                review.getUser().getUsername(),
                review.getReviewText(),
                review.getRating(),
                review.getDate()
        );
    }
}
