package com.example.socialnetworkapp.auth.service;

import com.example.socialnetworkapp.auth.model.RefreshToken;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;

import java.util.Optional;

public interface RefreshTokenService {

    Optional<RefreshToken> findByTokenAndUsername(String token, String username);

    void deleteByToken(String token);

    RefreshToken generateRefreshToken(String username);

}
