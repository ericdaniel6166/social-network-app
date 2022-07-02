package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.exception.ResourceNotFoundException;
import com.example.socialnetworkapp.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken findByToken(String token) throws ResourceNotFoundException;

    void deleteByToken(String token);

    RefreshToken generateRefreshToken();

}
