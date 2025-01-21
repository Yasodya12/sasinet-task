package com.sasinet.sasinetTask.service;

import com.sasinet.sasinetTask.DTO.UserDTO;

import javax.naming.AuthenticationException;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    UserDTO authenticateUser(UserDTO userDTO) ;
}
