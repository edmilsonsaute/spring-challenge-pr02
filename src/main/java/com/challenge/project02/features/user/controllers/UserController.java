package com.challenge.project02.features.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.project02.features.user.DTOs.UserRequestDTO;
import com.challenge.project02.features.user.DTOs.UserResponseDTO;
import com.challenge.project02.features.user.services.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.registerUser(userRequestDTO);
        return ResponseEntity.status(201).body(userResponseDTO);
    }
}
