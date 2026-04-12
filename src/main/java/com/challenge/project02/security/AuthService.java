package com.challenge.project02.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.project02.features.user.DTOs.UserAuthLoginDTO;
import com.challenge.project02.features.user.DTOs.UserAuthResponseDTO;
import com.challenge.project02.features.user.repositories.UserRepository;

@Service
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager,UserDetailsService userDetailsService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public UserAuthResponseDTO login(UserAuthLoginDTO request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String token = jwtService.generateToken(userDetails);

        return new UserAuthResponseDTO(token);
    }
}
