package com.example.socialnetworkapp.auth.service;

import com.example.socialnetworkapp.auth.dto.AuthenticationResponseDTO;
import com.example.socialnetworkapp.auth.dto.RefreshTokenRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface AuthService {

    SimpleResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) throws SocialNetworkAppException;

    SimpleResponseDTO verifyAccount(String token) throws SocialNetworkAppException;

    AuthenticationResponseDTO signIn(SignInRequestDTO signInRequestDTO) throws SocialNetworkAppException;

    AuthenticationResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) throws SocialNetworkAppException;

    SimpleResponseDTO signOut(RefreshTokenRequestDTO refreshTokenRequestDTO) throws SocialNetworkAppException;
}
