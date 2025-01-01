package org.example.backend.services;

import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserAuthenticationService userAuthenticationService;
    private final String userDeleted = "User deleted successfully!";

    public UserService(UserRepository userRepository,
                       UserAuthenticationService userAuthenticationService) {
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
    }

    public String getCurrentUsername() {
        return userAuthenticationService.getCurrentUsername();
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(getCurrentUsername()).orElse(null);
    }
  
    public User getUserByGmail(String email) {
        return userRepository.findByEmail(email.toLowerCase()).orElse(null);
    }

    public ResponseEntity<String> verifyUserExistenceByGmail(String email) {
        Optional<User> user = userRepository.findByEmail(email.toLowerCase());

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This Email already exists! Please log in.");
        }
        return ResponseEntity.ok("Email is unique and could be registered.");
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return null;
        }
        UserDTO userDTO = new UserDTO(user.get().getId(), user.get().getUsername(),
                user.get().getEmail(), user.get().getDateOfBirth(), user.get().getDateCreated());
        return userDTO;
    }

    public ResponseEntity<UserDTO> getUserDTOByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = new UserDTO(user.get().getId(), user.get().getUsername(),
                user.get().getEmail(), user.get().getDateOfBirth(), user.get().getDateCreated());
        return ResponseEntity.ok(userDTO);
    }

    public ResponseEntity<String> updateUser(Integer id, UserDTO userDTO) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User userToUpdate = User.builder()
                .id(user.get().getId())
                .username(userDTO.username())
                .password(user.get().getPassword())
                .email(userDTO.email())
                .dateOfBirth(userDTO.dateOfBirth())
                .dateCreated(user.get().getDateCreated())
                .build();
        userRepository.save(userToUpdate);
        return ResponseEntity.ok("User updated successfully!");
    }

    public ResponseEntity<String> deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(userDeleted);
    }

    public ResponseEntity<String> deleteUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteByUsername(username);
        return ResponseEntity.ok(userDeleted);
    }

    public Long getUsersCount() {
        return userRepository.count();
    }

    public List<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(),
                        user.getEmail(), user.getDateOfBirth(), user.getDateCreated()))
                .toList();
    }
}
