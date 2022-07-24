package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.auth.model.RefreshToken;
import com.example.socialnetworkapp.auth.repository.RefreshTokenRepository;
import com.example.socialnetworkapp.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Optional<RefreshToken> findByTokenAndUsername(String token, String username) {
        log.debug("Find refresh token by token and username, token: {}, username: {}", token, username);
        return refreshTokenRepository.findByTokenAndUsername(token, username);
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public RefreshToken generateRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUsername(username);
        return refreshTokenRepository.saveAndFlush(refreshToken);
    }
}
