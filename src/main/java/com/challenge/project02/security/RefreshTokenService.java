package com.challenge.project02.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.challenge.project02.features.user.DTOs.UserAuthResponseDTO;
import com.challenge.project02.features.user.entities.RefreshToken;
import com.challenge.project02.features.user.entities.User;
import com.challenge.project02.features.user.repositories.RefreshTokenRepository;
import com.challenge.project02.features.user.repositories.UserRepository;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository,
            JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken generateRefreshToken(UserDetails userDetails, String tokenValue) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        Date expirationDate = jwtService.extractExpiration(tokenValue);
        LocalDateTime expiryDateTime = convertToLocalDateTime(expirationDate);

        RefreshToken token = new RefreshToken();
        token.setToken(tokenValue);
        token.setUser(user);
        token.setExpiryDate(expiryDateTime);
        token.setRevoked(false);

        return refreshTokenRepository.save(token);
    }

    public void revokeToken(String tokenValue) {
        RefreshToken token = refreshTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Token nao encontrado"));

        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        List<RefreshToken> tokens = refreshTokenRepository.findByUser(user);
        tokens.forEach(token -> token.setRevoked(true));
        refreshTokenRepository.saveAll(tokens);
    }

    public RefreshToken validateAndGetRefreshToken(String tokenValue) {
        // 1. Verifica se o token existe no banco
        RefreshToken storedToken = refreshTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (storedToken.isRevoked()) {
            throw new RuntimeException("Refresh token has been revoked");
        }

        if (storedToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token has expired");
        }

        if (!jwtService.isTokenExpired(tokenValue)) {
            throw new RuntimeException("Invalid refresh token");
        }

        return storedToken;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
