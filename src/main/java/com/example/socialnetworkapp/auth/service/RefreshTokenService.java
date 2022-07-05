package com.example.socialnetworkapp.auth.service;

import com.example.socialnetworkapp.auth.model.RefreshToken;
import com.example.socialnetworkapp.exception.ResourceNotFoundException;

public interface RefreshTokenService {

    RefreshToken findByToken(String token) throws ResourceNotFoundException;

    void deleteByToken(String token);

    RefreshToken generateRefreshToken();

}
