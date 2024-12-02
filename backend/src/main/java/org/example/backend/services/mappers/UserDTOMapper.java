package org.example.backend.services.mappers;


import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Maps a User entity to a UserDTO , which is a data transfer object
 * that will be displayed in user profile.
 */
@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getDateCreated()
        );
    }
}
