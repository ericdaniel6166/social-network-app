package com.example.socialnetworkapp.auth.service.impl;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.auth.model.RefreshToken;
import com.example.socialnetworkapp.auth.repository.RefreshTokenRepository;
import com.example.socialnetworkapp.auth.service.RefreshTokenService;
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
        log.debug("Find refresh token by token, token: {}", token);
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
