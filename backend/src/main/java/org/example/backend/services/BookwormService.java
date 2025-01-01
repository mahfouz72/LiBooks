package org.example.backend.services;

import org.example.backend.models.entities.User;
import org.example.backend.models.dtos.BookwormDTO;
import org.example.backend.repositories.ConnectionsRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.example.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BookwormService {

    private final String userNotFoundMessage = "User not found";
    private final UserRepository userRepository;
    private final ConnectionsRepository connectionRepository;

    public BookwormService(UserRepository userRepository,
                           ConnectionsRepository connectionRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

    public BookwormDTO getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(userNotFoundMessage));
        int followersCount = connectionRepository.countFollowers(user);
        int followingCount = connectionRepository.countFollowing(user);
        return new BookwormDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getDateCreated(),
                followersCount,
                followingCount,
                false
        );
    }

    /**
     * Returns Bookworm Data
    */
    public BookwormDTO getBookwormProfile(String username, String currentUsername) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(userNotFoundMessage));
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException(userNotFoundMessage));
        int followersCount = connectionRepository.countFollowers(user);
        int followingCount = connectionRepository.countFollowing(user);
        boolean isFollowing = connectionRepository
                .existsByFollowerAndFollowing(currentUser.getId(), user.getId());

        return new BookwormDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getDateCreated(),
                followersCount,
                followingCount,
                isFollowing
        );
    }
}

