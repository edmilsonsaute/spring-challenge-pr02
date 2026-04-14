package com.challenge.project02.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.challenge.project02.features.user.DTOs.UserAuthLoginDTO;
import com.challenge.project02.features.user.DTOs.UserAuthResponseDTO;
import com.challenge.project02.features.user.entities.RefreshToken;
import com.challenge.project02.features.user.entities.User;


@Service
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager,UserDetailsService userDetailsService, RefreshTokenService refreshTokenService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    public UserAuthResponseDTO login(UserAuthLoginDTO request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        String acessToken = jwtService.generateAccessToken(userDetails);

        refreshTokenService.generateRefreshToken(userDetails, refreshToken);
        return new UserAuthResponseDTO(acessToken,refreshToken);
    }

      public UserAuthResponseDTO refreshAccessToken(String refreshTokenValue) {
        String token = refreshTokenValue.replace("Bearer ", "");
        
        RefreshToken storedToken = refreshTokenService.validateAndGetRefreshToken(token);

        User user = storedToken.getUser();
        String username = user.getUsername();
    
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        
        String newAccessToken = jwtService.generateAccessToken(userDetails);

        return new UserAuthResponseDTO(newAccessToken, refreshTokenValue);
    }

    public void logout(String token){
        refreshTokenService.revokeToken(token);
    }

    public void logoutAllDevices(User user){
        refreshTokenService.revokeAllUserTokens(user);
    }
}
