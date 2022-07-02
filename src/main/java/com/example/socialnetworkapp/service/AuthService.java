package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.dto.SignInRequestDTO;
import com.example.socialnetworkapp.dto.SignInResponseDTO;
import com.example.socialnetworkapp.dto.SignUpRequestDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface AuthService {

    SimpleResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) throws SocialNetworkAppException;

    SimpleResponseDTO verifyAccount(String token) throws SocialNetworkAppException;

    SignInResponseDTO signIn(SignInRequestDTO signInRequestDTO) throws SocialNetworkAppException;
}
