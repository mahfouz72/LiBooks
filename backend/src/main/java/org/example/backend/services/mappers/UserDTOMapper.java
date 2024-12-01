package org.example.backend.services.mappers;


import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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
