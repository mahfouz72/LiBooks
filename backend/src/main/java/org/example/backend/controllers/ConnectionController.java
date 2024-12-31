package org.example.backend.controllers;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.services.ConnectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connection")
public class ConnectionController {
    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping("/follow")
    public void followUser(@RequestParam String followingUsername,
                                           @RequestParam String followerUsername) {
        connectionService.followUser(followingUsername, followerUsername);
    }

    @DeleteMapping("/unfollow")
    public void unfollowUser(@RequestParam String followingUsername,
                                             @RequestParam String followerUsername) {
        connectionService.unfollowUser(followingUsername, followerUsername);
    }

    @GetMapping("/followers")
    public ResponseEntity<List<UserDTO>> getFollowers(@RequestParam String username) {
        return connectionService.getAllFollowers(username);
    }

    @GetMapping("/followings")
    public ResponseEntity<List<UserDTO>> getFollowings(@RequestParam String username) {
        return connectionService.getAllFollowings(username);
    }
}
