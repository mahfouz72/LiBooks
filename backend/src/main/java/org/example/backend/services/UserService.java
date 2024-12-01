package org.example.backend.services;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.example.backend.services.mappers.UserDTOMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserDTOMapper userDTOMapper;

    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    public UserDTO register(User user) {
        User savedUser = userRepository.save(user);
        return userDTOMapper.apply(savedUser);
    }
}
