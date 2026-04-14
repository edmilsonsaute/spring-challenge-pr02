package com.challenge.project02.features.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.project02.features.user.entities.RefreshToken;
import com.challenge.project02.features.user.entities.User;

import java.time.LocalDateTime;
import java.util.List;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUser(User user);

    void deleteByUser(User user);

    void deleteByExpiryDateBefore(LocalDateTime date);
    
}
