package org.example.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.backend.models.dtos.UserDTO;
import org.example.backend.services.ConnectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connection")
@Tag(name = "Connection API", description = "Follower-following endpoints description")
public class ConnectionController {
    private ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping("/follow/{followingUsername}/{followerUsername}")
    public void followUser(@PathVariable("followingUsername") String followingUsername,
                                           @PathVariable("followerUsername") String followerUsername) {
        connectionService.followUser(followingUsername, followerUsername);
    }

    @DeleteMapping("/unfollow/{followingUsername}/{followerUsername}")
    public void unfollowUser(@PathVariable("followingUsername") String followingUsername,
                                             @PathVariable("followerUsername") String followerUsername) {
        connectionService.unfollowUser(followingUsername, followerUsername);
    }

    @Operation(
            summary = "Getting all user followers",
            description = "Retrieves a list of all followers of a user"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved items")
    @ApiResponse(responseCode = "404", description = "No items found")
    @GetMapping("/followers/{username}")
    public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable("username") String username) {
        return connectionService.getAllFollowers(username);
    }

    @GetMapping("/followings/{username}")
    public ResponseEntity<List<UserDTO>> getFollowings(@PathVariable("username") String username) {
        return connectionService.getAllFollowings(username);
    }

    @GetMapping("/number/followers/{username}")
    public ResponseEntity<Integer> getNumberOfFollowers(@PathVariable("username") String username) {
        return connectionService.getNumberOfFollowers(username);
    }

    @GetMapping("/number/followings/{username}")
    public ResponseEntity<Integer> getNumberOfFollowings(@PathVariable("username") String username) {
        return connectionService.getNumberOfFollowings(username);
    }
}
