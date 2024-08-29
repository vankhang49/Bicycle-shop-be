package com.project.bicycleshopbe.service.permission.impl;

import com.project.bicycleshopbe.model.permission.RefreshToken;
import com.project.bicycleshopbe.repository.permission.IUserRepository;
import com.project.bicycleshopbe.repository.permission.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private IUserRepository userRepository;

    public RefreshToken createRefreshToken(String email){
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(email))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1800000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public void updateRefreshToken(RefreshToken refreshToken){
        refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public boolean checkAndDeleteExpiredToken(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            return true;
        }
        return false;
    }

    public void removeLastRefreshTokenByUserId(Long userId){
        refreshTokenRepository.deleteLastTokenByUserId(userId);
    }

    public void removeRefreshTokenByToken(String token){
        refreshTokenRepository.deleteTokenByRefreshToken(token);
    }

}