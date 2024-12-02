package org.example.backend.services.mappers;

import org.example.backend.models.dtos.UserRegistrationDTO;
import org.example.backend.models.entities.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Maps a UserRegistrationDTO to a User entity.
 * This is used when registering a new user.
 */
@Service
public class UserRegistrationDTOMapper implements Function<UserRegistrationDTO, User> {
    public User apply(UserRegistrationDTO userRegistrationDTO) {
        return User.builder()
                .username(userRegistrationDTO.username())
                .email(userRegistrationDTO.email())
                .password(userRegistrationDTO.password())
                .build();
    }
}
