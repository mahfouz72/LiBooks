package org.example.backend.services;

import org.example.backend.models.dtos.ActivityDTO;
import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ActivityService {

    private final ConnectionService connectionService;
    private final UserAuthenticationService userAuthenticationService;
    private final ReviewService reviewService;

    public ActivityService(ConnectionService connectionService,
                           UserAuthenticationService userAuthenticationService,
                           ReviewService reviewService) {
        this.connectionService = connectionService;
        this.userAuthenticationService = userAuthenticationService;
        this.reviewService = reviewService;
    }

    public List<ActivityDTO> getAllActivities() {
        final String currentUsername = userAuthenticationService.getCurrentUsername();
        ResponseEntity<List<UserDTO>> getAllFollowingResponse =
                connectionService.getAllFollowings(currentUsername);
        List<UserDTO> followings = getAllFollowingResponse.getBody();
        List<ActivityDTO> activities = new ArrayList<>();

        if (followings != null) {
            for (UserDTO following : followings) {
                List<ActivityDTO> followingActivities = getFollowingActivities(following.id());
                activities.addAll(followingActivities);
            }
        }

        activities.sort(Comparator.comparing(ActivityDTO::date).reversed());
        final int count = 10;
        return activities.subList(0, Math.min(count, activities.size()));
    }

    /**
     * Get all activities of a follower.
     * It includes all reviews of the follower.
     *
     * @param followerId the id of the follower
     * @return a list of activities of the follower
     */
    private List<ActivityDTO> getFollowingActivities(Integer followerId) {
        List<ActivityDTO> activities = new ArrayList<>();
        List<Review> followingReviews = reviewService.getReviewsByUserId(followerId);

        for (Review review : followingReviews) {
            final String followingUsername = review.getUser().getUsername();
            final String reviewedBookTitle = review.getBook().getBookTitle();
            ActivityDTO activity = ActivityDTO.builder()
                    .username(followingUsername)
                    .bookName(reviewedBookTitle)
                    .date(review.getDate())
                    .rating(review.getRating())
                    .reviewText(review.getReviewText())
                    .build();
            activities.add(activity);
        }

        return activities;
    }
}
