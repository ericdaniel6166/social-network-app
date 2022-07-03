package com.example.socialnetworkapp.service.impl;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.RefreshToken;
import com.example.socialnetworkapp.repository.RefreshTokenRepository;
import com.example.socialnetworkapp.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken findByToken(String token) throws ResourceNotFoundException {
        log.info("Start find refresh token by token, token: {}", token);
        return refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new ResourceNotFoundException("Refresh token " + token));
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.saveAndFlush(refreshToken);
    }
}
