package com.sasinet.sasinetTask.controller;

import com.sasinet.sasinetTask.DTO.LogInDTO;
import com.sasinet.sasinetTask.DTO.UserDTO;
import com.sasinet.sasinetTask.configuration.JwtUtil;
import com.sasinet.sasinetTask.entity.User;
import com.sasinet.sasinetTask.service.UserService;
import com.sasinet.sasinetTask.util.ErrorResponse;
import com.sasinet.sasinetTask.util.LoginRes;
import com.sasinet.sasinetTask.util.RegistrationSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final JwtUtil util;
    private final UserService userService;

    public UserController(JwtUtil util, UserService userService) {
        this.util = util;
        this.userService = userService;
    }
    // End point for user register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        System.out.println("inside resgister controller");

            // Register the user
            UserDTO registeredUser = userService.registerUser(userDTO);

            // Prepare a response message
            RegistrationSuccessResponse response = new RegistrationSuccessResponse(
                    "User registration successful",
                    registeredUser.getEmail(),
                    registeredUser.getUsername()
            );

            // Return the success message with HTTP 200 status
            return ResponseEntity.ok(response);

    }
    // End point for user login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LogInDTO userDTO) {

        System.out.println("inside controller"+userDTO);
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setUsername(userDTO.getUsername());
        userDTO2.setPassword(userDTO.getPassword());
        String token = util.createToken(userDTO2);

            UserDTO userDTO1 = userService.authenticateUser(userDTO2);
            return ResponseEntity.ok(new LoginRes(userDTO1.getEmail(),userDTO1.getId(),token));

    }
}

