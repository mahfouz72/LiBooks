package org.example.backend.services;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.Connection;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.ConnectionsRepository;
import org.example.backend.services.mappers.UserDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConnectionService {
    private final ConnectionsRepository connectionsRepository;
    private final UserService userService;
    private final UserDTOMapper userDTOMapper;

    public ConnectionService(ConnectionsRepository connectionsRepository,
                             UserService userService, UserDTOMapper userDTOMapper) {
        this.connectionsRepository = connectionsRepository;
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
    }

    public void followUser(String followingUsername, String followerUsername) {
        if(followingUsername.equals(followerUsername)) {
            throw new IllegalStateException("User cannot be a follower to him/her self");
        }

        User following = userService.getUserByUsername(followingUsername);
        User follower = userService.getUserByUsername(followerUsername);

        if(connectionsRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new IllegalStateException("Already following this user");
        }

        Connection connection = new Connection();
        connection.setFollower(follower);
        connection.setFollowing(following);

        connectionsRepository.save(connection);
    }

    public void unfollowUser(String followingUsername, String followerUsername) {
        if(followingUsername.equals(followerUsername)) {
            throw new IllegalStateException("User cannot unfollow him/her self");
        }

        User following = userService.getUserByUsername(followingUsername);
        User follower = userService.getUserByUsername(followerUsername);

        if(!connectionsRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new IllegalStateException("This connection does not exist");
        }

        connectionsRepository.deleteByFollowerAndFollowing(follower, following);
    }

    public ResponseEntity<List<UserDTO>> getAllFollowers(String userName) {
        User user = userService.getUserByUsername(userName);
        return ResponseEntity.ok(connectionsRepository.findByFollowing(user).stream()
                .map(connection -> userDTOMapper.apply(connection.getFollower()))
                .collect(Collectors.toList()));
    }

    public ResponseEntity<List<UserDTO>> getAllFollowings(String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(connectionsRepository.findByFollower(user).stream()
                .map(connection -> userDTOMapper.apply(connection.getFollowing()))
                .collect(Collectors.toList()));
    }

    public ResponseEntity<Integer> getNumberOfFollowers(String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(connectionsRepository.countFollowers(user));
    }

    public ResponseEntity<Integer> getNumberOfFollowings(String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(connectionsRepository.countFollowing(user));
    }
}
