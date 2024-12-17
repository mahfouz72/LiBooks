package org.example.backend.services;

import org.example.backend.exceptions.UsernameAlreadyExistsException;
import org.example.backend.models.dtos.UserDTO;
import org.example.backend.models.dtos.UserRegistrationDTO;
import org.example.backend.models.entities.User;
import org.example.backend.repositories.UserRepository;
import org.example.backend.security.JWTService;
import org.example.backend.services.mappers.UserDTOMapper;
import org.example.backend.services.mappers.UserRegistrationDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {

    private UserRepository userRepository;
    private UserDTOMapper userDTOMapper;
    private UserRegistrationDTOMapper userRegistrationDTOMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    private UserService userService;
    public UserAuthenticationService(UserRepository userRepository,
                                     UserDTOMapper userDTOMapper,
                                     UserRegistrationDTOMapper userRegistrationDTOMapper,
                                     PasswordEncoder passwordEncoder,
                                     AuthenticationManager authenticationManager,
                                     JWTService jwtService,
                                     UserService userService) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.userRegistrationDTOMapper = userRegistrationDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public UserDTO register(UserRegistrationDTO userRegistrationDTO) {
        checkUsernameUniqueness(userRegistrationDTO);

        User user = userRegistrationDTOMapper.apply(userRegistrationDTO);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        return userDTOMapper.apply(savedUser);
    }

    private void checkUsernameUniqueness(UserRegistrationDTO userRegistrationDTO) {
        String username = userRegistrationDTO.username();
        if (userRepository.existsUserByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }
    }

    public ResponseEntity<String> login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username, password
                        ));

        ResponseEntity<String> response;
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(username);
            response = new ResponseEntity<>(token, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public ResponseEntity<String> loginByGmail(String gmail) {
        User user = userService.getUserByGmail(gmail);
        ResponseEntity<String> response;

        if (user != null) {
            String username = user.getUsername();
            response = new ResponseEntity<>(jwtService.generateToken(username), HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("User is not registered", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }


}
