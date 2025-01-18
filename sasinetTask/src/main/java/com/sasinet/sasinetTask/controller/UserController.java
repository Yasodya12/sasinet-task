package com.sasinet.sasinetTask.controller;

import com.sasinet.sasinetTask.DTO.LogInDTO;
import com.sasinet.sasinetTask.DTO.UserDTO;
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
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService; // Dependency Injection through constructor
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        System.out.println("inside resgister controller");
        try {
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
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error. Please try again later."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LogInDTO userDTO) {

        System.out.println("inside controller"+userDTO);
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setUsername(userDTO.getUsername());
        userDTO2.setPassword(userDTO.getPassword());
        try {
            UserDTO userDTO1 = userService.authenticateUser(userDTO2);
            return ResponseEntity.ok(new LoginRes(userDTO1.getEmail(),userDTO1.getId()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}

