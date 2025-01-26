package com.sasinet.sasinetTask.service.impl;


import com.sasinet.sasinetTask.DTO.UserDTO;
import com.sasinet.sasinetTask.Repositry.UserRepository;
import com.sasinet.sasinetTask.entity.User;
import com.sasinet.sasinetTask.service.UserService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // This is the funtion for user register
    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        // Check if username or email is already in use
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Check if passwords match
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }



        // Convert DTO to Entity
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encrypt the password
        user.setEmail(userDTO.getEmail());

        // Save the user
        User savedUser = userRepository.save(user);

        // Convert Entity back to DTO
        UserDTO responseDTO = new UserDTO();
        responseDTO.setUsername(savedUser.getUsername());
        responseDTO.setEmail(savedUser.getEmail());

        return responseDTO;
    }

    // Method for user authentication (Login)
    public UserDTO authenticateUser(UserDTO userDTO) {
        // Check if the user exists
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // Verify the password
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Convert User entity to UserDTO and return
        return convertToDTO(user);
    }


    // Method to convert User entity to UserDTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                null,  // Don't need to return confirmPassword in the DTO
                user.getEmail()
        );
    }
}

