package com.example.socialnetworkapp.service;

import com.example.socialnetworkapp.dto.RegisterRequestDTO;
import com.example.socialnetworkapp.dto.SimpleResponseDTO;
import com.example.socialnetworkapp.exception.SocialNetworkAppException;

public interface AuthService {

    SimpleResponseDTO signUp(RegisterRequestDTO registerRequestDTO) throws SocialNetworkAppException;

    SimpleResponseDTO verifyAccount(String token) throws SocialNetworkAppException;
}
