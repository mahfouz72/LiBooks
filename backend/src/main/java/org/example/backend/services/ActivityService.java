package org.example.backend.services;

import org.example.backend.models.dtos.ActivityDTO;
import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        ResponseEntity<List<UserDTO>> getAllFollowersResponse =
                connectionService.getAllFollowers(currentUsername);
        List<UserDTO> followers = getAllFollowersResponse.getBody();
        List<ActivityDTO> activities = new ArrayList<>();

        if (followers != null) {

            for (UserDTO follower : followers) {
                List<ActivityDTO> followerActivities = getFollowerActivities(follower.id());
                activities.addAll(followerActivities);
            }
        }

        return activities;
    }

    /**
     * Get all activities of a follower.
     * It includes all reviews of the follower.
     *
     * @param followerId the id of the follower
     * @return a list of activities of the follower
     */
    private List<ActivityDTO> getFollowerActivities(Integer followerId) {
        List<ActivityDTO> activities = new ArrayList<>();
        List<Review> followerReviews = reviewService.getReviewsByUserId(followerId);

        for (Review review : followerReviews) {
            final String followerUsername = review.getUser().getUsername();
            final String reviewedBookTitle = review.getBook().getBookTitle();
            ActivityDTO activity = ActivityDTO.builder()
                    .username(followerUsername)
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
