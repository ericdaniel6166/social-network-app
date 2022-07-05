package com.example.socialnetworkapp.auth.service;

import com.example.socialnetworkapp.auth.dto.SignInRequestDTO;
import com.example.socialnetworkapp.auth.dto.SignInResponseDTO;
import com.example.socialnetworkapp.auth.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface AuthService {

    SimpleResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) throws SocialNetworkAppException;

    SimpleResponseDTO verifyAccount(String token) throws SocialNetworkAppException;

    SignInResponseDTO signIn(SignInRequestDTO signInRequestDTO) throws SocialNetworkAppException;
}
