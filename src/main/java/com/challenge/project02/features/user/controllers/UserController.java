package com.challenge.project02.features.user.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.project02.features.user.DTOs.UserAuthLoginDTO;
import com.challenge.project02.features.user.DTOs.UserAuthResponseDTO;
import com.challenge.project02.features.user.DTOs.UserRequestDTO;
import com.challenge.project02.features.user.DTOs.UserResponseDTO;
import com.challenge.project02.features.user.services.UserService;
import com.challenge.project02.security.AuthService;

@RestController
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }
    
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.registerUser(userRequestDTO);
        return ResponseEntity.status(201).body(userResponseDTO);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserAuthResponseDTO> login(@RequestBody UserAuthLoginDTO userAuthLoginDTO){
        UserAuthResponseDTO userLogin = authService.login(userAuthLoginDTO);
        return ResponseEntity.status(200).body(userLogin);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String refreshToken){
        String token = refreshToken.replace("Bearer", "");
        authService.logout(token);
        return ResponseEntity.ok("Logout com sucesso");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> listarUsuarios(){
        List<UserResponseDTO> usuarios = userService.listarUsuarios();
        return ResponseEntity.status(200).body(usuarios);
    }

}
