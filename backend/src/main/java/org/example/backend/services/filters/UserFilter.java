package org.example.backend.services.filters;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserFilter implements SearchFilter {
    private final UserRepository userRepository;

    public UserFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> applyFilter(String query) {
        return userRepository.findByUsernameContainingIgnoreCase(query)
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(),
                        user.getDateOfBirth(), user.getDateCreated()))
                .collect(Collectors.toList());
    }
}
